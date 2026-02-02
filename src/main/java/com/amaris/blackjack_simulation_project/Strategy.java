package com.amaris.blackjack_simulation_project;

public interface Strategy {
    BlackjackAction checkSoftStrategy(Card dealerCard, Hand hand) throws Exception;

    BlackjackAction checkHardStrategy(Card dealerCard, Hand hand) throws Exception;

    BlackjackAction checkPairStrategy(Card dealerCard, Hand hand) throws Exception;

    BlackjackAction checkSurrenderStrategy(Card dealerCard, Hand hand);


}
