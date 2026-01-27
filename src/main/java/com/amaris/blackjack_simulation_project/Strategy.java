package com.amaris.blackjack_simulation_project;

public interface Strategy {
    BlackjackAction checkSoftStrategy(Card dealerCard, Hand hand);

    BlackjackAction checkHardStrategy(Card dealerCard, Hand hand);

    BlackjackAction checkPairStrategy(Card dealerCard, Hand hand);

    BlackjackAction checkSurrenderStrategy(Card dealerCard, Hand hand);


}
