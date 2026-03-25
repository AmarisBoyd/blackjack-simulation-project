package com.amaris.blackjack_simulation_project.strategy;

import com.amaris.blackjack_simulation_project.model.BlackjackAction;
import com.amaris.blackjack_simulation_project.model.Card;
import com.amaris.blackjack_simulation_project.model.Hand;

public interface Strategy {
    BlackjackAction checkSoftStrategy(Card dealerCard, Hand hand) throws Exception;

    BlackjackAction checkHardStrategy(Card dealerCard, Hand hand) throws Exception;

    BlackjackAction checkPairStrategy(Card dealerCard, Hand hand) throws Exception;

    BlackjackAction checkSurrenderStrategy(Card dealerCard, Hand hand);

    //Method to get the name of the strategy in use by a player
    default String strategyName() {
        return this.getClass().getSimpleName();
    }

}
