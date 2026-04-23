package com.amaris.blackjack_simulation_project.model;

import java.util.ArrayList;

//Abstract class that both dealer and player will extend
public abstract class Person {
    TableRules rules;

    Person() {


    }




    public abstract TableRules getTableRules();

    public abstract int dealCard(ArrayList<Card> Shoe, int index);
}
