package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;


public class Dealer extends Person {
    private Hand dealerHand;

    public Dealer() {
        this.dealerHand = new Hand();
        this.rules = new TableRules();
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
        if (this.handScore > 21 && !(this.dealerHand.getIsSoft())) {
            return 1;
        }
        this.handScore = this.dealerHand.getScore();
        // if the dealer has 21
        if (this.handScore == 21)
            //stay
            return 1;
        if (this.rules.getHitSoft17()) {
            if (this.handScore >= 17) {
                if (this.dealerHand.getIsSoft()) {
                    return 0;
                } else return 1;
            } else return 0;


        } else {
            if (this.handScore > 16) {
                return 1;
            }

            return 0;
        }


    }

    public void checkTableState(Player[] players, int playerCount) {
        int dealerScore = this.getDealerHand().getScore();
        int playerScore;
        // if the dealer has bust
        if (dealerScore > 21) {
            //do dealer bust actions
            dealerBust(players, playerCount);
        } else {
            //loop through all the players
            for (int i = 0; i < playerCount; i++) {
                Player currentPlayer = players[i];
                //if the player has bust out completely
                if (currentPlayer.isHasBust()) {
                    //increment losses for each hand

                    for (int j = 0; j <= currentPlayer.getTotalHands(); j++) {
                        currentPlayer.incrementLosses();

                    }

                }
                //if they haven't
                else {
                    //loop through all of their hands
                    for (int j = 0; j <= currentPlayer.getTotalHands(); j++) {
                        playerScore = currentPlayer.getHand()[j].getScore();
                        //if the player has a higher score
                        if (playerScore > dealerScore) {
                            //increase their win record
                            currentPlayer.incrementWins();
                        }
                        //if the current hand is lower than dealers cards
                        if (playerScore < dealerScore) {
                            // increment losses
                            currentPlayer.incrementLosses();
                        }
                        //if current hand equals dealers score
                        if (playerScore == dealerScore) {
                            //increment pushes
                            currentPlayer.incrementPushes();
                        }
                    }
                }
            }
        }


    }

    private void dealerBust(Player[] players, int playerCount) {
        //loop through all players
        for (int i = 0; i < playerCount; i++) {
            Player player = players[i];
            //if they haven't bust
            if (!player.isHasBust()) {
                //loop through each hand
                for (int j = 0; j <= player.getTotalHands(); j++) {
                    //increment win counter for each hand that still exist
                    if (player.getHand()[j].isHasBust()) {
                        player.incrementLosses();
                    } else
                        player.incrementWins();
                }
            }
        }

    }

    public void cleanTable(Player[] players, int playerCount, ArrayList<Card> discard) {
        //For each player in reverse order
        for (int i = playerCount - 1; i >= 0; i--) {

            //current player again for readability
            Player currentPlayer = players[i];
            //while they have a hand put it into the discard pile
            for (int j = currentPlayer.getTotalHands(); j >= 0; --j) {
                currentPlayer.getHand()[j] = clearHand(currentPlayer.getHand()[j], discard);

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

    private Hand clearHand(Hand hand, ArrayList<Card> discard) {
        //check if hand has aces
        for (Card card : hand.getCards()) {
            // if the ace value is one
            if (card.getValue() == 1) {
                //set the value to 11
                card.setValue(11);

            }
        }

        //if the hand didn't bust
        if (!hand.isHasBust()) {
            //add all the cards to the discard
            discard.addAll(hand.getCards());
        }

        //either way reset the hand
        return new Hand();
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
                    currentPlayer.setHasBust(currentPlayer.getHand()[i].isHasBust());

                }
            }


        }

        //add the hand that bust to the discard
        discard.addAll(currentPlayer.getHand()[currentHand].getCards());
        //clear the current hand so the score is 0
        currentPlayer.getHand()[currentHand].setScore(0);
        //set it so the now empty hand has bust
        currentPlayer.getHand()[currentHand].setHasBust(true);


    }

    //Check if the player has bust by iterating over every card in the hand functional
    //but does potentially unnecessary checks
    public boolean checkBust(Hand hand) {
        //on the initial hand
        if (hand.getCards().size() == 2) {
            //check if its two aces
            if (hand.getCards().get(0).getValue() == 11 && hand.getCards().get(1).getValue() == 11) {
                //if it is return that it hasn't busted but don't alter the cards
                return false;
            }
        }

        //if the score of the current hand isn't higher than 21
        if (!(hand.getScore() > 21))
            //return false so we don't bother with other logic
            return false;
        //if the hand is not soft
        if (!hand.checkSoft(hand.getCards())) {
            //and hand score is over 21 return true

            return hand.getScore() > 21;
        }
        for (int i = 0; i < hand.getHandSize(); i++) {
            //check if the card has a value of 11
            if (hand.getCards().get(i).getValue() == 11) {
                //check if the score is over 21
                if (hand.getScore() > 21) {
                    //if the score is set the cards value to 1 for
                    // some reason java is treating
                    // all aces of the same suit and rank as the same object so create a new card
                    hand.getCards().set(i, new Card(hand.getCards().get(i).getSuit(), hand.getCards().get(i).getRank(), 1));
                    //update the score
                    hand.updateScore();
                    //set it so the hand is no longer soft
                    hand.setIsSoft(false);
                }
                //if the hand is less than 21 but there is an 11 value
                if (hand.getScore() < 21) {
                    //set it back to being soft
                    hand.setIsSoft(true);
                }


            }


        }
        //if at the end of the check hand is not soft and is still over 21
        //return that the player bust
        return !hand.getIsSoft() && hand.getScore() > 21;
    }

    //Separate dealer check for potential differences later on
    public boolean dealerCheckBust(Hand hand) {

        //if the score of the current hand isn't higher than 21
        if (!(hand.getScore() > 21))
            //return false so we don't bother with other logic
            return false;
        //if the hand is not soft
        if (!hand.checkSoft(hand.getCards())) {
            //and hand score is over 21 return true

            return hand.getScore() > 21;
        }
        for (int i = 0; i < hand.getHandSize(); i++) {
            //check if the card has a value of 11
            if (hand.getCards().get(i).getValue() == 11) {
                //check if the score is over 21
                if (hand.getScore() > 21) {
                    //if the score is set the cards value to 1 for
                    // some reason java is treating
                    // all aces of the same suit and rank as the same object so create a new card
                    hand.getCards().set(i, new Card(hand.getCards().get(i).getSuit(), hand.getCards().get(i).getRank(), 1));
                    //update the score
                    hand.updateScore();
                    //set it so the hand is no longer soft
                    hand.setIsSoft(false);
                }
                //if the hand is less than 21 but there is an 11 value
                if (hand.getScore() < 21) {
                    //set it back to being soft
                    hand.setIsSoft(true);
                }


            }
        }
        //if at the end of the check hand is not soft and is still over 21
        //return that the player bust
        return !hand.getIsSoft() && hand.getScore() > 21;
    }

    //Potential optimized method of checking bust using the location of soft aces
    //Non-functional
    public boolean checkBustSoftAce(Hand hand) {
        //local variables for readability
        ArrayList<Integer> softAces = hand.getSoftAceLocations();
        ArrayList<Card> handCards = hand.getCards();

        //if the score of the current hand isn't higher than 21
        if (!(hand.getScore() > 21))
            //return false so we don't bother with other logic
            return false;
        //if the hand is not soft
        if (!hand.checkSoft(hand.getCards())) {
            //and hand score is over 21 return true

            return hand.getScore() > 21;
        }
        for (int i = 0; i < softAces.size(); i++) {
            if (hand.getScore() > 21) {
                //set the value of the ace to 1
                int location = softAces.get(i);
                handCards.get(location).setValue(1);
                //decrement the hand score by 10
                hand.setScore(hand.getScore() - 10);
                softAces.remove(i);


            } else {
                //if the hand score is no longer  above 21 leave the loop
                return false;
            }
        }

        return true;
    }

    public Hand getDealerHand() {
        return this.dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }


    public Card getUpCard() {
        return dealerHand.getCards().getFirst();
    }

    @Override
    public TableRules getTableRules() {
        return this.rules;
    }

    @Override
    public String toString() {

        return "Dealer{" +
                "hand=" + dealerHand.toString() +
                ", handScore=" + handScore +
                '}';
    }
}
