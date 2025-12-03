package com.amaris.blackjack_simulation_project;
//
public class Card {
    //String to hold suit and rank of card 
    String suit="";
    String rank="";
    String suitAndRank;
    
    //Integer to hold value of card
    int value;
    boolean toString;
    public Card(){
      this.suit="";
      this.rank ="";
      this.value=0;
      this.suitAndRank="";
    }
    //Constructor to initialize suitAndRank and value
    public Card(String suitAndRank, int value) {
    this.suitAndRank = suitAndRank;
    this.value = value;     }
    public Card(String suit,String rank, int value) {
    this.suit=suit;
    this.rank=rank;
    this.value = value;  
    this.suitAndRank = suit+" "+rank;
  }
 

   public void setSuit(String suit) {
        this.suit = suit;
    }

  

    public void setRank(String rank) {
        this.rank = rank;}
        
    //Getter method for Rank and suit 
    public int getValue() {
        return this.value;
    }
    //Getter method for value
    public String getSuitAndRank() {
        return this.suitAndRank;
    }
    //toString method for easy printing
    
      public String getRank() {
        return rank;
    }
        public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
       this.suitAndRank= this.suit+" "+this.rank;
       return this.suitAndRank + ": " + this.value;
       
    }
    
}