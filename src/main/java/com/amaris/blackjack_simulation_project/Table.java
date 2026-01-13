package com.amaris.blackjack_simulation_project;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Class to represent a blackjack table
public class Table {
    //Variables to hold game state
    //Dealer object to keep logic of table consistent
    Dealer dealer;
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
    private int playerCount = 0;

    // constructor using default blackjack  house rules
    public Table() {
        this.rules = new TableRules();
        this.players = new Player[this.rules.getMaxplayers()];
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


        }

    }

    public void playerActions() {
        Player currentPlayer;
        //initialize a result to keep track of what each player decides to do
        int result = 0;
        // loop through all players
        for (int i = 0; i < this.playerCount; i++) {
            //select current player from the array for easier access
            currentPlayer = this.players[i];
            //while this player hasn't stood on their last hand
            while (result != 1) {
                //check the strategy based on the hand
                result = currentPlayer.strategy(dealer.getUpCard());
                //if they say give them a card
                if (result == 0) {
                    result = hit(currentPlayer);

                }

                //player doubles down on the hand  only gets one card
                if (result == 2) {
                    result = doubleDown(currentPlayer);

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
                    if(currentPlayer.isHasSplit()&&!currentPlayer.isSplitAces()){
                        //if the current hand is not equal to the max number of total hands
                        if (!(currentPlayer.getCurrentHand() == currentPlayer.getTotalHands())) {
                            //set the current hand for the player to the next hand they have
                            currentPlayer.setCurrentHand(currentPlayer.getCurrentHand() + 1);
                            // reset result so the loop continues
                            result = 0;
                    }

                    }
                }



            }

        }

    }

    private int noResplitAces(Player currentPlayer) {

        Hand originalhand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        Hand seconHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()+1];
        //set the split tag
        currentPlayer.setHasSplit(true);
        //split the aces into two hands
        seconHand.addCard(originalhand.getCards()[1]);
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

        return workingHand.getCards()[0].getValue() == 11;
    }

    private int splitHand(Player currentPlayer) {
        return 0;
    }



    private int hit(Player currentPlayer) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        currentPlayer.dealCard(shoe);
        //If the player bust return 1 since they can no longer hit

        if ( dealer.checkBust(workingHand) ) {
            dealer.clearBust(currentPlayer, discard);
            return 1;
        }
        // otherwise return 0 so the loop continues
        return 0;
    }

    private int doubleDown(Player currentPlayer) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        // give the player the next card
        currentPlayer.dealCard(shoe);
        //check if they bust in case of doubling on hard number
        if ( dealer.checkBust(workingHand) ) {
            dealer.clearBust(currentPlayer, discard);
        }
        //return that the player can no longer hit on this hand
        return 1;
    }

    public void dealerActions() {
        boolean dealerStop = false;
        while (!dealerStop) {

        }
        //This feels like a very bad idea passing a table that includes the dealer itself to the dealer
        this.dealer.checkTableState(this);
        this.dealer.cleanTable(this);

        //Have dealer play out their hand according to standard blackjack rules
        // Placeholder implementation while loop until dealer's hand value is 17 or higher or busts

    }
    //clean up the table after a hand


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

    public void loadDeck() {
        //location of cards config file make this selectable later
        String src = "src/main/resources/Cards.json";
        Card[] cards;
        try {
            File cardsFile = new File(src);
            ObjectMapper objectMapper = new ObjectMapper();
            cards = objectMapper.readValue(cardsFile, Card[].class);
            this.setDeck(cards);

        } catch (DatabindException d) {
            d.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    // load up the shoe based on max number of decks and cards in the "deck"
    public void loadShoe() {
        for (int i = 0; i < this.rules.getDeckNumber(); i++) {
            this.shoe.addAll(List.of(deck));


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

    public Card[] getDeck() {
        return deck;
    }

    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    public int getCutPosition() {
        return this.cutCard;
    }


    @Override
    public String toString() {
        StringBuilder shoeString = new StringBuilder("The shoe contains:\n");
        for (Card card : shoe) {
            shoeString.append(card.toString()).append("\n");
        }

        return shoeString.toString();
    }


    public int getPlayerCount() {
        return this.playerCount;
    }
}

    


