package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends Person {
    private Hand dealerHand;
    public Dealer() {
        this.dealerHand = new Hand();

    }

    @Override
    public void dealCard(Card card) {
        //add the card to the hand
        this.dealerHand.addCard(card);
        //update the current score
        this.handScore=this.dealerHand.getScore();
    }

    public int strategy(){

        if(this.handScore>16&&!this.dealerHand.getIsSoft(

        )){
            return 1;
        }

        return 0;
    }
    public void checkTableState(Table table){

        // at the end of the hand check who won and lost

    }
    public void cleanTable(Table table) {
        //For each player in reverse order
        //while they have a hand put it into the discard pile
        //add cards to discard
        // remove from hand
        // decrement hand size
        //if ace set value back to 11
        //for dealer put hand on top of discard

    }
    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }


    public Card getUpCard(){
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
        //clear the players hand
        currentPlayer.getHand()[currentHand]=new Hand();
        //check if the player has split
        if(!currentPlayer.isHasSplit()){
            //if they haven't then they only had this hand so they have bust and are out of the game
            currentPlayer.setHasBust(true);
        }

    }
}
