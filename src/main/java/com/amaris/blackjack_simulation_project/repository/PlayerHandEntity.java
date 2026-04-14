package com.amaris.blackjack_simulation_project.repository;

import java.util.List;

//Used to tracking the choices made for each player hand
public class PlayerHandEntity {
    //id for this specific player hand
    private Long id;
    //Key for the round that this belongs to
    private RoundEntity round;
    //id for the player who had this round
    private Long playerId;
    // list of choices made during this players turn
    private List<String> choiceHistory;
    // Cards the player started with
    private String initialHand;


}
