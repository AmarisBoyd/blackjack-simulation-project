package com.amaris.blackjack_simulation_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//Temporary data structure used to test connecting to database
//TODO decide if i want to use a data structure like this or the @Transient on all the fields we don't store
@Entity
@Data

public class DataPlayer {
    @Id
    @GeneratedValue
    private int id;
    private int wins;
    private int losses;
    private int draws;


}
