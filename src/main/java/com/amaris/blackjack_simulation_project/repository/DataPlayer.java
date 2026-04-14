package com.amaris.blackjack_simulation_project.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//Data Structure to hold the player data before its transferred to the database
@Entity
@Data

public class DataPlayer {
    @Id
    @GeneratedValue
    private int id;
    private int wins;
    private String strategyName;
    private int losses;
    private int draws;


}
