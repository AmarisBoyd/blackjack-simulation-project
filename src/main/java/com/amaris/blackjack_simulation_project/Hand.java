package com.amaris.blackjack_simulation_project;

public class Hand {
    //Create array with max handize of 11 cards (theoretical max in blackjack) this could be an arraylist but for simplicity using array
    private Card[] cards = new Card[11];
    //interger to track current score of hand
    private int score;
    //boolean to track if hand is a soft hand defaults to false
    private boolean isSoft= false;
    //boolean to track if hand is a pair defaults to false
    private boolean isPair= false;
    //integer to track current hand size
    private int handSize;
    
    public Hand() {
        this.score=0;
        this.handSize=0;
    }   
    //Method to add card to hand
    public void addCard(Card card) {
    
        this.cards[handSize]= card;
        handSize++;
        //Update score
        updateScore();  }

    //Method to update score of hand
    private void updateScore() {
        for (int i=0; i<handSize; i++) {
            this.score += this.cards[i].getValue();
            }}


   //check for soft hand
      public boolean checksoft (Card[] hand) {
        /*  Placeholder implementation the logic works but it relies on Aces being able to be changed 
        between 1 and 11 and that could be wasteful to reset after each hand */
        for (Card card : hand) {
            //check if any card is an Ace acting as 11
            if (card.getValue() == 11) {
                return true;
            }
        }
        //if no Ace found acting as 11 return false
        return false;      }    

   
    //check for pair
        public boolean checkPair (Card[] hand) {
        // Check if both cards have the same value
          return hand[0].getValue()== hand[1].getValue(); 
    }

     //Setters and getters
    //setter method for isPair
    public void setIsPair(boolean isPair) {
        this.isPair = isPair;       }
    //setter method for isSoft
    public void setIsSoft(boolean isSoft) {
        this.isSoft = isSoft;       }      
    //setter method for handSize
    public void setHandSize(int handSize) {
        this.handSize = handSize;       }  
    //Setter method for score (for debugging purposes)
    public void setScore(int score) {
        this.score = score;       } 
    //Setter method for cards (for debugging purposes)
    public void setCards(Card[] cards) {
        this.cards = cards;    
        this.handSize = cards.length;
        for (Card card : this.cards) {
            this.score += card.getValue();

        }      
    }

    //Getter method for isPair
    public boolean getIsPair() {
        return this.isPair; }
     //Getter method for isSoft
    public boolean getIsSoft() {
        return this.isSoft; }
    //Getter method for handSize
    public int getHandSize() {  
        return this.handSize; } 
    //Getter method for score
    public int getScore() {
        return this.score;
    }
    //Getter method for cards
    public Card[] getCards() {
        return this.cards;  }

    @Override
    public String toString() {
        StringBuilder handString = new StringBuilder("Hand: ");
        for (int i = 0; i < handSize; i++) {
            handString.append(cards[i].toString());
            if (i < handSize - 1) { 
                handString.append(", ");
            }
        }
        handString.append(" | Score: ").append(score);
    
        return handString.toString();

    }
    
}