package com.amaris.blackjack_simulation_project;

import java.util.ArrayList;

//Abstract class that both dealer and player will extend
public abstract class Person {
    TableRules rules;

    Person() {


    }

    public abstract void dealCard(ArrayList<Card> Shoe);


    public abstract TableRules getTableRules();
}
