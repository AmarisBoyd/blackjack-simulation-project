package com.amaris.blackjack_simulation_project.repository;

//Class that represents where each player is during each round
public record SeatingEntity(
        String playerID,
        int seatIndex

) {
}

