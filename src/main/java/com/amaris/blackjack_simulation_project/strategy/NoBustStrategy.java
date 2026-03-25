package com.amaris.blackjack_simulation_project.strategy;

import com.amaris.blackjack_simulation_project.model.BlackjackAction;
import com.amaris.blackjack_simulation_project.model.Card;
import com.amaris.blackjack_simulation_project.model.Hand;

//Class for a player that follows a no-bust strategy (yes, they exist ive seen them)
public class NoBustStrategy implements Strategy {

    @Override
    public BlackjackAction checkSoftStrategy(Card dealerCard, Hand hand) {
        return null;
    }

    @Override
    public BlackjackAction checkHardStrategy(Card dealerCard, Hand hand) {
        return null;
    }

    @Override
    public BlackjackAction checkPairStrategy(Card dealerCard, Hand hand) {
        return null;
    }

    @Override
    public BlackjackAction checkSurrenderStrategy(Card dealerCard, Hand hand) {
        return null;
    }


    //Soft strategy not needed for no-bust player since they will never have a soft hand over 11
    //Pair strategy follows normal rules as no-bust player can still split
    //Double strategy follows normal rules as no-bust 


}