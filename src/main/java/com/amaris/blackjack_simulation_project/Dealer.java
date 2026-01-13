package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dealer extends Person {
    private Hand dealerHand;

    public Dealer() {
        this.dealerHand = new Hand();

    }

    @Override
    public void dealCard(ArrayList<Card> shoe) {
        //add the card to the hand
        this.dealerHand.addCard(shoe.getLast());
        shoe.removeLast();
        //update the current score
        this.handScore = this.dealerHand.getScore();
    }

    public int strategy() {

        if (this.handScore > 16 && !this.dealerHand.getIsSoft(

        )) {
            return 1;
        }

        return 0;
    }

    public void checkTableState(Player[] players) {
        // if the dealer has bust
        if (this.handScore > 21) {
            //do dealer bust actions
            dealerBust(players);
        } else {
            //loop through all the players
            for (Player currentPlayer : players) {
                //if the player has bust
                if (currentPlayer.isHasBust()) {
                    //increment losses
                    currentPlayer.incrementLosses();
                }
                //if they haven't
                else {
                    //loop through all of their hands
                    for (int j = 0; j < currentPlayer.getTotalHands(); j++) {
                        //if the player has a higher score
                        if (currentPlayer.getHand()[j].getScore() > this.handScore) {
                            //increase their win record
                            currentPlayer.incrementWins();
                        }
                        //if the current hand is lower than dealers cards
                        if (currentPlayer.getHand()[j].getScore() < this.handScore) {
                            // increment losses
                            currentPlayer.incrementLosses();
                        }
                        //if current hand equals dealers score
                        if (currentPlayer.getHand()[j].getScore() == this.handScore) {
                            //increment pushes
                            currentPlayer.incrementDraws();
                        }
                    }
                }
            }
        }


    }

    private void dealerBust(Player[] players) {
        //loop through all players
        for (Player player : players) {
            //if they haven't bust
            if (!player.isHasBust()) {
                //loop through each hand
                for (int i = 0; i < player.getTotalHands(); i++) {
                    //increment win counter for each hand that still exist
                    if (!player.getHand()[i].isHasBust()) {
                        player.incrementWins();
                    }
                }
            }
        }

    }

    public void cleanTable(Player[] players, ArrayList<Card> discard) {
        //For each player in reverse order
        for (int i = players.length - 1; i >= 0; i--) {

            //current player again for readability
            Player currentPlayer = players[i];
            //while they have a hand put it into the discard pile
            for (int j = currentPlayer.getTotalHands() - 1; j >= 0; --j) {
                clearHand(currentPlayer.getHand()[j], discard);

            }

            // decrement hand size
            currentPlayer.setTotalHands(0);
            //set current hand to 0
            currentPlayer.setCurrentHand(0);
            //set has split to false
            currentPlayer.setHasSplit(false);
            //set has bust to false
            currentPlayer.setHasBust(false);
            //set has split aces to false
            currentPlayer.setSplitAces(false);

        }
        //for dealer put hand on top of discard
        clearHand(this.dealerHand, discard);
        this.dealerHand = new Hand();
        this.handScore = 0;


    }

    private void clearHand(Hand hand, ArrayList<Card> discard) {
        //check if hand has aces
        for (Card card : hand.getCards()) {
            // if the ace value is one
            if (card.getValue() == 1) {
                //set the value to 11
                card.setValue(11);

            }
        }
        //if the hand has bust

        if (hand.isHasBust()) {
            //clear the hands flag but don't add the cards to the discard
            hand = new Hand();
        }
        //if hand didn't bust
        else {
            //add all the cards to the discard
            discard.addAll(List.of(hand.getCards()));
            // make the hand a new hand so the flags are reset
            hand = new Hand();

        }
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }


    public Card getUpCard() {
        return dealerHand.getCards()[0];
    }

    public void burnCard(ArrayList<Card> shoe, ArrayList<Card> discard) {
        //add the last card of the shoe to the discard
        discard.add(shoe.getLast());
        //remove last card of the shoe from
        shoe.remove(shoe.getLast());
    }

    //Clear up cards from player and check if they are still in the round
    public void clearBust(Player currentPlayer, ArrayList<Card> discard) {
        // get the current hand for readability
        int currentHand = currentPlayer.getCurrentHand();

        //add the hand that bust to the discard
        discard.addAll(List.of(currentPlayer.getHand()[currentHand].getCards()));
        //set it so the now empty hand has bust
        currentPlayer.getHand()[currentHand].setHasBust(true);
        //check if the player has split
        if (!currentPlayer.isHasSplit()) {
            //if they haven't then they only had this hand so they have bust and are out of the game
            currentPlayer.setHasBust(true);
        }
        //if they have
        if (currentPlayer.isHasSplit()) {
            //check if there is a next hand
            if (!(currentPlayer.getCurrentHand() < currentPlayer.getTotalHands())) {
                //if there isn't check if each hand has bust
                for (int i = 0; i < currentPlayer.getTotalHands(); i++) {
                    //if one hand hasn't the player is still in the game
                    if (!currentPlayer.getHand()[i].isHasBust()) {
                        currentPlayer.setHasBust(false);
                    }

                }
            }


        }


    }

    public boolean checkBust(Hand hand) {
        //local variables for readability
        ArrayList<Integer> softAces = hand.getSoftAceLocations();
        Card[] handCards = hand.getCards();

        //if the score of the current hand isn't higher than 21
        if (hand.getScore() < 21)
            //return false so we don't bother with other logic
            return false;
        //if the hand is not soft
        if (!hand.checkSoft(hand.getCards())) {
            //and hand score is over 21 return true

            return hand.getScore() > 21;
        }
        for (Integer i : softAces) {
            if (hand.getScore() > 21) {
                //set the value of the ace to 1
                handCards[softAces.get(i)].setValue(1);
                //decrement the hand score by 10
                hand.setScore(hand.getScore() - 10);


            } else {
                //if the hand score is no longer  above 21 leave the loop
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "hand=" + Arrays.toString(hand) +
                ", handScore=" + handScore +
                '}';
    }
}
