package com.amaris.blackjack_simulation_project.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.amaris.blackjack_simulation_project.model.BlackjackAction.*;

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
        BlackjackAction action = HIT;
        // loop through all players
        for (int i = 0; i < this.playerCount; i++) {
            //select current player from the array for easier access
            currentPlayer = this.players[i];

            //while this player hasn't stood on their last hand
            while (action != BlackjackAction.STA) {
                if (currentPlayer.getHand()[currentPlayer.getCurrentHand()].getScore() == 21) {
                    action = STA;
                    break;
                }
                //check if previous action caused the player to bust
                this.dealer.checkBust(currentPlayer.getHand()[currentPlayer.getCurrentHand()]);
                //check the rules this is for observers later
                checkRules(currentPlayer);
                //check the strategy based on the hand
                action = currentPlayer.strategy(dealer.getUpCard());

                switch (action) {
                    case HIT:
                        //if they say give them a card
                        action = hit(currentPlayer);
                        break;
                    case DOS, DOH:
                        //call double down that will check if they are allowed to
                        action = doubleDown(currentPlayer, action);
                        break;
                    case SDH, SPL, SDS:
                        //player splits
                        //check if the pair is a pair of aces
                        if (checkAces(currentPlayer)) {
                            action = splitAces(currentPlayer, action);
                        }
                        //if not two aces split the hands
                        else
                            action = splitHand(currentPlayer, action);
                        break;
                    case STA:
                        if (checkIfNextHand(currentPlayer)) {
                            action = HIT;
                        }
                        break;
                    case SUR:
                        //logic for surrender to be done later for now just stay
                        action = STA;
                        break;
                    case DEA:
                        System.err.println(dealer.getUpCard());
                        action = STA;
                        break;



                }
                //at the end of the player choice check for last hand
                this.checkLastHand();


            }

        }

    }

    private boolean checkIfNextHand(Player currentPlayer) {
        //player stands check if we need to move to next split
        //if the current player has split and they are not aces
        if (currentPlayer.isHasSplit() && !currentPlayer.hasSplitAces()) {
            //if the current hand is not equal to the max number of total hands
            if (!(currentPlayer.getCurrentHand() == currentPlayer.getTotalHands())) {
                //set the current hand for the player to the next hand they have
                currentPlayer.setCurrentHand(currentPlayer.getCurrentHand() + 1);
                //if the current hand still only has one card
                if (currentPlayer.getHand()[currentPlayer.getCurrentHand()].getHandSize() == 1) {
                    //give them another card
                    hit(currentPlayer);
                    return true;
                }


            }
        } else if (currentPlayer.isHasSplit() && currentPlayer.hasSplitAces()) {
            //TODO handle split ace logic

        }
        //if they have not split at all return false as there is no next hand
        return false;
    }

    private void checkRules(Player currentPlayer) {
        //check for rules regarding splits
        if (currentPlayer.isHasSplit()) {
            //if the player can double after split set can double to true otherwise set it to false
            currentPlayer.setCanDouble(rules.canDoubleAfterSplit());
            //if the player has split aces
            if (currentPlayer.hasSplitAces()) {
                currentPlayer.setAbleToSplit(rules.canResplitAces());
            }
            //if the players hands equal max splits -1 (indexing from 0) set it so they cant split
            if (currentPlayer.getTotalHands() == rules.getMaxSplits() - 1) {
                currentPlayer.setAbleToSplit(false);
            }
        }
        //check for other rules
    }

    private void checkLastHand() {
        if (this.shoe.size() < this.getCutPosition()) {
            lastHand = true;
        }
    }


    private BlackjackAction splitAces(Player currentPlayer, BlackjackAction currentAction) {
        //if the player can split
        if (currentPlayer.isAbleToSplit()) {
            //increment total hands for player
            currentPlayer.setTotalHands(currentPlayer.getTotalHands() + 1);
            //reference to original hand
            Hand originalhand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
            //reference to the new hand that is being created
            Hand secondHand = currentPlayer.getHand()[currentPlayer.getCurrentHand() + 1];
            //set the split tag
            currentPlayer.setHasSplit(true);
            //take the second  ace from original hand and put it on the second hand
            secondHand.addCard(originalhand.getCards().remove(1));
            //set original hand size to one
            originalhand.setHandSize(1);
            //set the original hand so its no longer seen as a pair
            originalhand.setIsPair(false);
            //give original hand a card
            currentPlayer.dealCard(shoe);
            //if the rules allow resplitting of aces or hitting on split aces
            if (rules.canResplitAces() || rules.canHitSplitAces()) {
                //return hit so we can check if the current hand is a pair and then proceed
                return HIT;


            }
            //if neither of those are true we are done with the first hand
            else {
                //increment working hand
                currentPlayer.setCurrentHand(currentPlayer.getCurrentHand() + 1);
                //give next hand a card
                currentPlayer.dealCard(shoe);
                //since we know we are not splitting or hitting that hand either return stand
                return STA;
            }
        }
        //if the player cannot split the aces again  check which version of split was sent
        else {
            //if they wanted to split or stand return stand
            if (currentAction.getExpansion().equalsIgnoreCase("Split if double after split  is offered otherwise stand")) {
                return STA;
            }
            // if they wanted to split or hit return the results of hit
            else if (currentAction == SDH) {
                return hit(currentPlayer);
            }
            // if the action was a standard split
            else if (currentAction == SPL) {
                if (rules.canHitSplitAces()) {
                    //if they can hit split aces we need to figure out if they hit or stay for now returning hit
                    // to avoid the issue of it being an soft 12 which doesn't exist in most strategies
                    //TODO figure this out

                    return HIT;
                } else {
                    //if they cannot hit split aces and cannot split again just stand since they cant do anything
                    return STA;
                }
            }

        }
        //this should be unreachable but return stand  so the loop continues with the next hand
        //TODO add logging in the case this is ever reached
        return STA;
    }

    private boolean checkAces(Player currentPlayer) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];

        return workingHand.getCards().getFirst().getRank().equalsIgnoreCase("Ace");
    }

    private BlackjackAction splitHand(Player currentPlayer, BlackjackAction currentAction) {
        int currentHand = currentPlayer.getCurrentHand();
        int nextHand = currentPlayer.getCurrentHand() + 1;
        //if the player is not trying to split more times than allowed
        if (currentPlayer.isAbleToSplit()) {
            //increment the total number of hands the player has
            currentPlayer.setTotalHands(currentPlayer.getTotalHands() + 1);
            //take second card from current hand and place it in new hand
            currentPlayer.getHand()[nextHand].addCard(currentPlayer.getHand()[currentHand].getCards().remove(1));
            //decrement the current hands hand size
            currentPlayer.getHand()[currentHand].setHandSize(currentPlayer.getHand()[currentHand].getHandSize() - 1);
            //give them a new card to the current hand
            currentPlayer.dealCard(shoe);
            //unset pair so it doesn't skip checking
            currentPlayer.getHand()[currentHand].setIsPair(false);
            //set it so the table knows the player has split at least once
            currentPlayer.setHasSplit(true);
            //set the current hand to no longer a pair so they don't try to split
            currentPlayer.getHand()[currentPlayer.getCurrentHand()].setIsPair(false);

        } else {
            if (currentAction == SPL) {
                //if the player only sent split, and we are unable to split instead of a pair
                // Return hit this in combination with the false isAbleToSplit will cause it to default to hard totals
                return HIT;
            } else if (currentAction == SDS) {
                //if the player sent Split or Stand return stand
                return STA;
            } else if (currentAction == SDH) {
                //if the player returned split or hit then hit
                return hit(currentPlayer);
            }
        }
        //return HIT so the dealer loop continues
        return HIT;
    }


    private BlackjackAction hit(Player currentPlayer) {
        Hand workingHand = currentPlayer.getHand()[currentPlayer.getCurrentHand()];
        currentPlayer.dealCard(shoe);
        //If the player bust return STA since they can no longer hit
        if (dealer.checkBust(workingHand)) {
            dealer.clearBust(currentPlayer, discard);
            return STA;
        }
        // otherwise return Hit so the loop continues
        return HIT;
    }

    private BlackjackAction doubleDown(Player currentPlayer, BlackjackAction currentAction) {
        //if the player has split
        if (currentPlayer.isHasSplit()) {
            //check if they can double
            if (currentPlayer.canDouble) {
                // if they can give them a card use hit in case later strategy doubles a busting hand
                hit(currentPlayer);
                // then return stand so they can no longer hit
                return STA;
                // if the player can't double
            } else {
                //if the player sent double or hit
                if (currentAction == DOH) {
                    //return the result of hitting in case of bust
                    return hit(currentPlayer);
                }
                //if the player sent "double or stand"
                else if (currentAction == DOS) {
                    //return stand
                    return STA;
                }
            }
        } else {
            //if the player hasn't split hands they are allowed to double so give them the next card
            //use hit in case a later strategy doubles a busting card
            hit(currentPlayer);
            //Then return stand so the loop ends
            return STA;
        }
        // This should be un reachable
        return STA;
    }

    public void dealerTurn() {
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
                //after the dealer hits check if they bust
                if (this.dealer.dealerCheckBust(this.dealer.getDealerHand())) {
                    //if they have stop drawing cards
                    dealerStop = true;
                }
                //after hit check if it's the last hand
                this.checkLastHand();
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

    public Object getDeck() {
        return this.deck;
    }

    public TableRules getRules() {
        return this.rules;
    }
}

    


