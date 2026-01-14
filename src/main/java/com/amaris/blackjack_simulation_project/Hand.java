package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;

public class Hand {
    //Create array with max hand size of 11 cards (theoretical max in blackjack) this could be an arraylist but for simplicity using array
    private ArrayList<Card> cards;
    //integer to track current score of hand
    private int score;
    //boolean to track if hand is a soft hand defaults to false
    private boolean isSoft = false;
    //boolean to track if hand is a pair defaults to false
    private boolean isPair = false;
    //integer to track current hand size
    private int handSize;
    private boolean hasBust = false;
    //arraylist to keep track of where soft aces are in hand so we don't have to search
    private ArrayList<Integer> softAceLocations;


    public Hand() {
        this.score = 0;
        this.handSize = 0;
        this.cards = new ArrayList<>();

        softAceLocations = new ArrayList<>();
    }

    public Hand(Card card, Card card1) {
        this.cards.add(card);
        this.cards.add(card1);
    }

    public Hand(Hand hand) {
        this.cards = hand.cards;
    }

    //Method to add card to hand
    public void addCard(Card card) {
        this.cards.add(card);

        //if the added card is an ace
        if(card.getValue() == 11){

            //add that there is a soft ace at this spot
            softAceLocations.add(handSize);
            //set is soft to true
            isSoft = true;
        }
        //increment the hand size
        handSize++;
        //Update score
        updateScore();
    }

    //Method to update score of hand
    public void updateScore() {
        //reset the score
        this.score = 0;
        // count all the cards again and update the score
        for (int i = 0; i < handSize; i++) {
            int toAdd = this.cards.get(i).getValue();
            this.score = this.score + toAdd;
        }
    }


    //check for soft hand M
    public boolean checkSoft(ArrayList<Card> hand) {
        /*  This may now be obsolete since we are checking for soft aces at the time of adding card
        * */
        for (int i = 0; i < handSize; i++) {
            //check if any card is an Ace acting as 11
            if (hand.get(i).getValue() == 11) {
                return true;
            }
        }
        //if no Ace found acting as 11 return false
        return false;
    }


    //check for pair
    public boolean checkPair(ArrayList<Card> hand) {
        // Check if both cards have the same value
        return hand.getFirst().getValue() == hand.get(1).getValue();
    }

    //Getter method for isPair
    public boolean getIsPair() {
        return this.isPair;
    }

    //Setters and getters
    //setter method for isPair
    public void setIsPair(boolean isPair) {
        this.isPair = isPair;
    }

    //Getter method for isSoft
    public boolean getIsSoft() {
        return this.isSoft;
    }

    //setter method for isSoft
    public void setIsSoft(boolean isSoft) {
        this.isSoft = isSoft;
    }

    //Getter method for handSize
    public int getHandSize() {
        return this.handSize;
    }

    //setter method for handSize
    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    //Getter method for score
    public int getScore() {
        return this.score;
    }

    //Setter method for score (for debugging purposes)
    public void setScore(int score) {
        this.score = score;
    }

    //Getter method for cards
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    //Setter method for cards (for debugging purposes)
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
        this.handSize = cards.size();
        for (Card card : this.cards) {
            this.score += card.getValue();

        }
    }
    //getter for the locations of soft aces
    public ArrayList<Integer> getSoftAceLocations() {
        return this.softAceLocations;
    }

    //mutators for has bust
    public boolean isHasBust() {
        return this.hasBust;
    }

    public void setHasBust(boolean hasBust) {
        this.hasBust = hasBust;
    }
    @Override
    public String toString() {
        StringBuilder handString = new StringBuilder("Hand: ");
        for (int i = 0; i < handSize; i++) {
            handString.append(cards.get(i).toString());
            if (i < handSize - 1) {
                handString.append(", ");
            }
        }
        handString.append(" | Score: ").append(score);

        return handString.toString();

    }


}