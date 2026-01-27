package com.amaris.blackjack_simulation_project;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.amaris.blackjack_simulation_project.BlackjackAction.*;

// Class to represent a blackjack table
public class Table {
    //Array to hold a single deck of cards for loading the shoe
    Card[] deck;
    //Array to hold "shoe" of cards (multiple decks)
    ArrayList<Card> shoe;
    //Arraylist to hold the discard pile
    ArrayList<Card> discard;
    //Array to hold players at the table
    Player[] players;
    //Object to hold specific table rules
    TableRules rules;
    //integer to keep track of where the cut card is to stop the shoe
    int cutCard = 0;
    //Variables to hold game state
    //Dealer object to keep logic of table consistent
    private Dealer dealer;
    private int playerCount = 0;
    //boolean to track if this is the last hand of the shoe
    private boolean lastHand;

    // constructor using default blackjack  house rules
    public Table() {
        this.rules = new TableRules();
        this.players = new Player[this.rules.getMaxPlayers()];
        this.dealer = new Dealer();
        shoe = new ArrayList<>();
        discard = new ArrayList<>();


    }


    public void dealInitialCards() {
        //Deal two cards to each player and dealer from the shoe
        for (int i = 0; i < 2; i++) {

            for (int j = 0; j < this.playerCount; j++) {

                players[j].dealCard(shoe);


            }
            //give the dealer the next card
            this.dealer.dealCard(shoe);
            //check if this is the last hand
            this.checkLastHand();

        }


    }

    public void playerTurn() {
        Player currentPlayer;
        //initialize a result to keep track of what each player decides to do
        BlackjackAction result = null;
        // loop through all players
        for (int i = 0; i < this.playerCount; i++) {
            //select current player from the array for easier access
            currentPlayer = this.players[i];
            //while this player hasn't stood on their last hand
            while (result != BlackjackAction.STAND) {
                //check the strategy based on the hand
                this.dealer.checkBust(currentPlayer.getHand()[currentPlayer.getCurrentHand()]);
                result = currentPlayer.strategy(dealer.getUpCard());
                //if they say give them a card
                if (result == HIT) {
                    result = hit(currentPlayer);

                }

                //If the player is trying to double
                if (result == DOUBLE_OR_HIT || result == DOUBLE_OR_STAND) {
                    //call double down that will check if they are allowed to
                    result = doubleDown(currentPlayer, result);

                }

                //player splits
                if (result == 3) {
                    //check if the pair is a pair of aces
                    if (checkAces(currentPlayer)) {
                        //set split aces to true
                        currentPlayer.setSplitAces(true);
                        //check if resplitting aces is allowed
                        if (!rules.getResplitAces()) {
                            result = noResplitAces(currentPlayer);
                        } else
                            //if it is split the aces
                            result = splitAces(currentPlayer);
                    }
                    //if not two aces split the hands
                    else
                        result = splitHand(currentPlayer);


                }

                //player stands check if we need to move to next split
                if (result == 1) {
                    //if the current player has split and they are not aces
                    if (currentPlayer.isHasSplit() && !currentPlayer.isSplitAces()) {
                        //if the current hand is not equal to the max number of total hands
                        if (!(currentPlayer.getCurrentHand() == currentPlayer.getTotalHands())) {
                            //set the current hand for the player to the next hand they have
                            currentPlayer.setCurrentHand(currentPlayer.getCurrentHand() + 1);
                            // reset result so the loop continues
                            result = 0;
                        }

                    }
                }

                //at the end of the player choice check for last hand
                this.checkLastHand();
            }

        }

    }

    private void checkLastHand() {
        if (this.shoe.size() < this.getCutPosition()) {
            lastHand = true;
        }
    }

    private int noResplitAces(Player currentPlayer) {

        Hand originalhand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        Hand seconHand = currentPlayer.getHand()[currentPlayer.getCurrentHand() + 1];
        //set the split tag
        currentPlayer.setHasSplit(true);
        //split the aces into two hands
        seconHand.addCard(originalhand.getCards().get(1));
        /*set original hand size to one since we are counting from 0 this will make it so
        the next time we add a card it will replace the ace we just gave out
        * */
        originalhand.setHandSize(1);

        //give original hand a card
        currentPlayer.dealCard(shoe);
        //increment working hand
        currentPlayer.setCurrentHand(1);
        //give next hand a card
        currentPlayer.dealCard(shoe);
        return 1;
    }

    private int splitAces(Player currentPlayer) {

        //check
        currentPlayer.setTotalHands(currentPlayer.getTotalHands() + 1);
        //take second card from current hand and put it in a new hand
        //since the player hasn't stood return 0
        return 0;
    }

    private boolean checkAces(Player currentPlayer) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];

        return workingHand.getCards().getFirst().getValue() == 11;
    }

    private int splitHand(Player currentPlayer) {
        int currentHand = currentPlayer.getCurrentHand();
        int nextHand = currentPlayer.getCurrentHand() + 1;
        //if the player is not trying to split more times than allowed
        if (currentPlayer.getTotalHands() < rules.getMaxSplits()) {
            //increment the total number of hands the player has
            currentPlayer.setTotalHands(currentPlayer.getTotalHands() + 1);
            //take second card from current hand and place it in new hand
            currentPlayer.getHand()[nextHand].addCard(currentPlayer.getHand()[currentHand].getCards().get(1));
            //decrement the current hands hand size
            currentPlayer.getHand()[currentHand].setHandSize(currentPlayer.getHand()[currentHand].getHandSize() - 1);
            //remove old last card
            currentPlayer.getHand()[currentHand].getCards().removeLast();
            currentPlayer.getHand()[currentHand].updateScore();
            //give them a new card
            currentPlayer.dealCard(shoe);
            //unset pair so it doesn't skip checking
            currentPlayer.getHand()[currentHand].setIsPair(false);
            //set it so the table knows the player has split at least once
            currentPlayer.setHasSplit(true);


        }
        //return zero so the dealer loop continues
        return 0;
    }


    private BlackjackAction hit(Player currentPlayer) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        currentPlayer.dealCard(shoe);
        //If the player bust return 1 since they can no longer hit

        if (dealer.checkBust(workingHand)) {
            dealer.clearBust(currentPlayer, discard);
            return STAND;
        }
        // otherwise return Hit so the loop continues
        return HIT;
    }

    private BlackjackAction doubleDown(Player currentPlayer, BlackjackAction currentAction) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        //if the player hasn't split hands
        if (currentPlayer.getTotalHands() == 0) {
            //they are allowed to double so give them the next card
            currentPlayer.dealCard(shoe);
            //Then return stand so the loop ends
            return STAND;
        }

        //if the player has split
        // And is trying to double or hit
        //check if they can double
        // if they can
        //give them a card
        // then return stand so they can no longer hit
        //if they cant
        //return hit but don't give them the card so there isn't a double hit

        //else if the player is trying to double or stand,
        // and they are allowed to double
        //give them a card
        // then return stand so they can no longer hit
        //if they cant
        //return Stand to end the loop
        //returning current action until logic is finished
        return currentAction;
    }

    public void dealerActions() {
        int result;
        //set the dealer to stop unless there are other players at the table
        boolean dealerStop = true;
        //check if there are still players
        for (int i = 0; i < this.playerCount; i++) {
            Player currentPlayer = this.players[i];
            //if there is at least one player who hasn't bust continue
            if (!currentPlayer.isHasBust()) {
                dealerStop = false;
                break;
            }

        }
        //while the dealer hasn't reached and endpoint
        while (!dealerStop) {
            //check the dealers strategy
            result = this.dealer.strategy();
            // if it returns one
            if (result == 1) {
                //stop
                dealerStop = true;

            } else {
                //the dealer hits
                this.dealer.dealCard(shoe);
                //after hit check if it's the last hand
                this.checkLastHand();
                //after the dealer hits check if they bust
                if (this.dealer.dealerCheckBust(this.dealer.getDealerHand())) {
                    //if they have stop drawing cards
                    dealerStop = true;
                }
            }


        }


    }


    public void shuffleShoe() {
        //Shuffle the shoe of cards
        Collections.shuffle(this.shoe);

    }

    public void cutShoe(int cutPosition) {
        //Create new shoe array to hold cut shoe
        ArrayList<Card> newShoe = new ArrayList<>();
        //Copy the end of the shoe from cutPosition to end into new shoe
        newShoe.addAll(this.shoe.subList(cutPosition, this.shoe.size()));
        //Append the start of the shoe to the end of the new shoe
        newShoe.addAll(this.shoe.subList(0, cutPosition));
        //Place the cut card approximately 1 deck into the back of the shoe
        this.cutCard = java.util.concurrent.ThreadLocalRandom.current().nextInt(40, 60);
        //clear the old shoe
        this.shoe.clear();
        //copy temporary shoe to working shoe
        this.shoe.addAll(newShoe);
        //Set the first card to be dealt to one after the last card in the shoe to simulate burning the first card
        dealer.burnCard(this.shoe, this.discard);

    }


    //method to read a JSON file that contains all the cards in a standard deck

    public void loadDeck() throws IOException {
        //location of cards config file make this selectable later
        String src = "src/main/resources/Cards.json";
        Card[] cards;
        try {
            File cardsFile = new File(src);
            ObjectMapper objectMapper = new ObjectMapper();
            cards = objectMapper.readValue(cardsFile, Card[].class);
            this.setDeck(cards);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }


    }


    // load up the shoe based on max number of decks and cards in the "deck"
    public void loadShoe() {
        for (int i = 0; i < this.rules.getDeckNumber(); i++) {
            for (Card card : this.deck) {
                shoe.add(new Card(card));
            }


        }
    }


    //Getters and setters banished to the near bottom like usual 


    public void addPlayer(Player player) {
        this.players[playerCount] = player;
        playerCount++;
    }

    public ArrayList<Card> getShoe() {
        return this.shoe;
    }

    //Setter for the shoe for making deterministic shoes
    public void setShoe(ArrayList<Card> testShoe) {
        this.shoe = testShoe;
    }

    public Card[] getDeck() {
        return deck;
    }

    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    public int getCutPosition() {
        return this.cutCard;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public String handResults() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.playerCount; i++) {
            result.append(this.players[i].getResults());
            result.append("\n");
        }
        result.append(this.dealer.toString());

        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder shoeString = new StringBuilder("The shoe contains:\n");
        for (Card card : shoe) {
            shoeString.append(card.toString()).append("\n");
        }

        return shoeString.toString();
    }

    public void addDealer(Dealer dealerOne) {
        this.dealer = dealerOne;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public ArrayList<Card> getDiscard() {
        return this.discard;
    }

    public void setDiscard(ArrayList<Card> expectedDiscard) {
        this.discard = expectedDiscard;
    }

    public String getDiscardToString() {
        StringBuilder shoeString = new StringBuilder("The discard contains:\n");
        for (Card card : this.discard) {
            shoeString.append(card.toString()).append("\n");
        }

        return shoeString.toString();
    }

    public boolean getLastHand() {
        return this.lastHand;
    }

    public void setLastHand(boolean b) {
        this.lastHand = b;
    }
}

    


