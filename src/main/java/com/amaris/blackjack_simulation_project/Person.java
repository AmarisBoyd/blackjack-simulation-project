package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;

//Abstract class that both dealer and player will extend
//TODO merge "handScore" and "score" having two variables for one value is causing logic mistakes
public abstract class Person {
    protected int handScore;
    TableRules rules;

    Person() {
        this.handScore = 0;

    }

    public abstract void dealCard(ArrayList<Card> Shoe);


    public int getHandScore() {
        return this.handScore;
    }

    public abstract TableRules getTableRules();
}
