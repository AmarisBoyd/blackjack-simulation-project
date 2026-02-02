package com.amaris.blackjack_simulation_project;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/*
Class to act as a "player"  holding the score of the hand  basic player will follow the basic strategy */
public class Player extends Person {
    protected int playerID;
    //Integer to track current hand for splits
    protected int currentHand = 0;
    //used to track if we have reached the end of the hands counting first hand as "hand Zero"
    protected int totalHands = 0;

    //Arrays to hold cards in hand multiple hands for splits for our each player will otherwise have only one hand
    protected Hand[] hands;
    //Strategy to hold player behavior
    Strategy strategy;
    //Record of wins losses and pushes for player
    protected int wins = 0;
    protected int losses = 0;
    protected int pushes = 0;
    //Array to hold current hand's cards for easier reading
    protected ArrayList<Card> handCards;
    //Copy of table rules so the players can make some choices 
    protected TableRules rules;
    // Boolean to track if the player has split defaulting to false
    protected boolean hasSplit = false;
    //Boolean to track if the player has split aces
    protected boolean splitAces = false;
    //boolean to track if player has busted
    protected boolean hasBust = false;
    // boolean to track if the player can split
    protected boolean ableToSplit = true;
    // boolean to track if player can double
    protected boolean canDouble = true;


    //Default constructor Uses most common blackjack rules
    public Player() {
        // Make the default table rules
        this.rules = new TableRules();
        //get max splits from the table rules add one to make total number of hands after splits
        int maxHands = rules.getMaxSplits() + 1;
        //create array to hold max 4 hands
        hands = new Hand[maxHands];
        //remember to initialize each hand in the array
        for (int i = 0; i < maxHands; i++) {
            hands[i] = new Hand();
        }
        this.strategy = new BasicStrategy();

    }

    // Create player based on given table rules
    public Player(TableRules rules) {
        //get max splits from the table rules add one to make total number of hands after splits
        int maxHands = rules.getMaxSplits() + 1;
        //create array to hold specified number of hands based on maxHands parameter
        hands = new Hand[maxHands];
        //remember to initialize each hand in the array
        for (int i = 0; i < maxHands; i++) {
            hands[i] = new Hand();
        }

    }

    //Method to decide what action to take based on dealer's card and player's hand
    public BlackjackAction strategy(Card dealerCard) {
        // Int to hold "choice" of what to do as an enum
        BlackjackAction decision;
        //Get the current hand and store it for easier reading
        Hand curHand = this.hands[currentHand];
        //Check to see if pair only on the first two cards
        if (curHand.getHandSize() == 2) {
            //if the current hand is a pair. Set isPair to true
            curHand.setIsPair(curHand.checkPair(curHand.getCards()));

        }
        //Check if current hand is  soft and if it is set isSoft to true
        curHand.setIsSoft(curHand.checkSoft(curHand.getCards()));
        //check to see if hand is a pair and player is allowed to split
        if (curHand.getIsPair() && isAbleToSplit()) {
            //check against pair strategy table
            try {
                decision = strategy.checkPairStrategy(dealerCard, curHand);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                decision = BlackjackAction.DEA;

            }
        }//if the player cant split and the hand is soft
        else if (curHand.getIsSoft() && !(curHand.getIsPair())) {
            //check against soft hand strategy table
            try {
                decision = strategy.checkSoftStrategy(dealerCard, curHand);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                decision = BlackjackAction.DEA;

            }
        } //if the current hand is neither soft nor a pair
        else {
            //check against hard hand strategy table
            try {
                decision = strategy.checkHardStrategy(dealerCard, curHand);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                decision = BlackjackAction.DEA;
            }
        }

        // Return the decision

        return decision;
    }

    //Debug helper  method to add cards to hand and see other functions work properly
    public void debugSetHand(ArrayList<Card> hand) {
        //add cards to first hand only for testing
        this.hands[currentHand].setCards(hand);


    }


    public void dealCard(ArrayList<Card> shoe) {
        //Add the last card of the shoe to current hand
        this.hands[currentHand].addCard(shoe.getLast());
        // then remove it from the shoe
        shoe.remove(shoe.getLast());
    }

    @Override
    public TableRules getTableRules() {
        return this.rules;
    }

    // method to reset the hands for testing
    public void resetHand() {
        int maxHands = rules.getMaxSplits() + 1;
        //create array to hold max 4 hands
        hands = new Hand[maxHands];
        //remember to initialize each hand in the array
        for (int i = 0; i < maxHands; i++) {
            hands[i] = new Hand();
        }
    }

    public Hand[] getHand() {
        return hands;
    }

    public int getCurrentHand() {
        return this.currentHand;
    }

    public void setCurrentHand(int i) {
        this.currentHand = i;
    }

    public int getTotalHands() {
        return this.totalHands;
    }

    public void setTotalHands(int i) {
        this.totalHands = i;
    }

    public boolean isHasSplit() {
        return hasSplit;
    }

    public void setHasSplit(boolean hasSplit) {
        this.hasSplit = hasSplit;
    }

    public boolean hasSplitAces() {
        return this.splitAces;
    }

    public void setSplitAces(boolean splitAces) {
        this.splitAces = splitAces;
    }

    public boolean isHasBust() {
        return hasBust;
    }

    public void setHasBust(boolean hasBust) {
        this.hasBust = hasBust;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementPushes() {
        this.pushes++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public int getLosses() {
        return this.losses;
    }

    public int getWins() {
        return this.wins;
    }

    public int getPushes() {
        return this.pushes;
    }


    public String getResults() {
        long totalGames = this.wins + this.pushes + this.losses;

        BigDecimal winPercent = BigDecimal.valueOf((wins * 100.00 / totalGames));
        StringBuilder handsString = new StringBuilder();
        handsString.append("[");
        for (Hand value : this.hands) {
            if (!value.getCards().isEmpty()) {
                handsString.append(value);
                handsString.append(" ");
            }

        }
        handsString.append("]");
        return "Player " + this.playerID + " { " +
                "Hands: " + handsString +
                ", wins=" + this.wins +
                ", losses=" + this.losses +
                ", pushes=" + this.pushes +
                ", win%= " + winPercent.doubleValue() +
                '}';
    }

    @Override
    public String toString() {
        return "Player{" +
                "currentHand=" + currentHand +
                ", hands=" + Arrays.toString(hands) +
                ", wins=" + wins +
                ", losses=" + losses +
                ", pushes=" + pushes +
                '}';
    }

    public void setPlayerID(int i) {
        this.playerID = i;
    }

    public boolean isCanDouble() {
        return this.canDouble;
    }

    public void setCanDouble(boolean b) {
        this.canDouble = b;
    }


    public void setAbleToSplit(boolean b) {
        this.ableToSplit = b;
    }

    public boolean isAbleToSplit() {
        return this.ableToSplit;
    }
}