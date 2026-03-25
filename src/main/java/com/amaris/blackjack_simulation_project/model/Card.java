package com.amaris.blackjack_simulation_project.model;

//
public class Card {
    //String to hold suit and rank of card 
    String suit = "";
    String rank = "";
    String suitAndRank;
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
        //TODO these constructors need to be input validated
        this.suitAndRank = suitAndRank;
        this.value = value;
    }

    //Constructor for tests when cards are created for just their value
    public Card(int value) {
        //Joker is often seen as wildcard, and it looks better when printing than having a null rank
        this.rank = "Joker";
        this.value = value;
    }

    public String getAbbrev() {
        if (this.suit.isEmpty() || this.rank.isEmpty() || this.value == 0)
            return "";
        String abbrev = "";
        if (this.value != 10) {
            abbrev += this.value;
        } else switch (this.rank.toLowerCase()) {
            case "jack":
                abbrev += "J";
                break;
            case "queen":
                abbrev += "Q";
                break;
            case "king":
                abbrev += "K";
                break;
            case "ace":
                abbrev += "A";
                break;
            case "ten":
                abbrev += "10";
                break;
            default:
                abbrev += "?";
                break;

        }
        abbrev += (this.suitAndRank.charAt(0));
        return abbrev.toUpperCase();
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


    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    //toString method for easy printing
    @Override
    public String toString() {
        this.suitAndRank = this.suit + " " + this.rank;
        return this.suitAndRank + ": " + this.value;

    }


}