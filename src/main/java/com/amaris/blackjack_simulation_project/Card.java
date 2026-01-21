package com.amaris.blackjack_simulation_project;

//
public class Card {
    //String to hold suit and rank of card 
    String suit = "";
    String rank = "";
    String suitAndRank;
    boolean toString;
    //Integer to hold value of card
    private int value;

    public Card() {
        this.suit = "";
        this.rank = "";
        this.value = 0;
        this.suitAndRank = "";
    }


    //Constructor to initialize suitAndRank and value
    public Card(String suitAndRank, int value) {
        this.suitAndRank = suitAndRank;
        this.value = value;
    }

    //Constructor that only cares about card value for testing
    public Card(int value) {
        this.value = value;
    }

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.suitAndRank = suit + " " + rank;
    }

    public Card(Card card) {
        this.suit = card.getSuit();
        this.rank = card.getRank();
        this.value = card.getValue();
        this.suitAndRank = card.getSuitAndRank();
    }

    //Getter method for Rank and suit
    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //Getter method for value
    public String getSuitAndRank() {
        return this.suitAndRank;
    }

    public String getRank() {
        return rank;
    }
    //toString method for easy printing

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        this.suitAndRank = this.suit + " " + this.rank;
        return this.suitAndRank + ": " + this.value;

    }


}