package com.amaris.blackjack_simulation_project;

public class Dealer extends Player {
    private Hand dealerHand;
    public Dealer() {
        this.dealerHand = new Hand();
        super();
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
    public Card getUpcard(){
        return dealerHand.getCards()[0];
    }
}
