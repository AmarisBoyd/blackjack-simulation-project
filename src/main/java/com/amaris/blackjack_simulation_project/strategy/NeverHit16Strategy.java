package com.amaris.blackjack_simulation_project.strategy;

import com.amaris.blackjack_simulation_project.model.BlackjackAction;
import com.amaris.blackjack_simulation_project.model.Card;
import com.amaris.blackjack_simulation_project.model.Hand;

public class NeverHit16Strategy extends BasicStrategy {
    @Override
    public BlackjackAction checkHardStrategy(Card dealerCard, Hand hand) throws Exception {
        //if the hand is a hard 16
        if (hand.getScore() == 16) {
            //stay
            return BlackjackAction.STA;
        } else {
            //otherwise default to basic strategy
            return super.checkHardStrategy(dealerCard, hand);
        }
    }
}
