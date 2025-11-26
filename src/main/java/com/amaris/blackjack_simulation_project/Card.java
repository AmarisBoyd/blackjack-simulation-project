package com.amaris.blackjack_simulation_project;
//
public class Card {
    //String to hold suit and rank of card 
    String suitAndRank;
    //Integer to hold value of card
    int value;
    //Constructor to initialize suitAndRank and value
    public Card(String suitAndRank, int value) {
    this.suitAndRank = suitAndRank;
    this.value = value;     }
    //Getter method for Rank and suit 
    public int getValue() {
        return this.value;
    }
    //Getter method for value
    public String getSuitAndRank() {
        return this.suitAndRank;
    }

    
}