package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;

//Abstract class that both dealer and player will extend
public abstract class Person {
    Card [] hand;
    protected int handScore;
    TableRules rules;
    Person(){
        this.handScore = 0;

    }
    public abstract void dealCard(ArrayList<Card> Shoe);


}
