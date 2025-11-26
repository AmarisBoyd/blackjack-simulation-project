package com.amaris.blackjack_simulation_project;

//TODO change access modifiers so that subclasses can access necessary methods and variables

/*
Class to act as a "player"  holding the score of the hand  basic player will follow the basic strategy */
public class Player {
    //Integer to hold score of hand will be updated by outside classes during gameplay
    private int handScore =0;
    //Arrays to hold cards in hand multiple hands for splits for our each player will otherwise have only one hand
    private Card[][] hand;
    //Boolean to track if hand is a pair initialized to false
    private Boolean isPair = false;
    //Boolean to track if hand is soft initialized to false
    private Boolean isSoft = false;

    //Record of wins losses and pushes for player
    private int wins =0;
    private int losses =0;
    private int pushes =0; 


    //Method to decide what action to take based on dealer's card and player's hand
    public int strategy(Card dealerCard, Card[] hand) {
         // Int to hold "choice" of what to do 0=hit 1=stand 2=double 3=split
         int decision =0;

         //Check for pair only on initial hand
        if (hand.length==2) {
            isPair= checkPair(hand);
            
           
        }
        //Check for soft hand
         isSoft = checksoft(hand);
       if (isPair) {
        //check against pair strategy table
        decision = checkPairStrategy(dealerCard, hand);
       }
       else if (isSoft) {
        //check against soft hand strategy table
        decision = checkSoftStrategy(dealerCard, hand);  
       }
       else {
        //check against hard hand strategy table
        decision = checkHardStrategy(dealerCard, hand);
       }
        // Return the decision
       return decision;
    }


    protected int checkPairStrategy(Card dealerCard, Card[] hand) {
        // Pair strategy table  rows represent player's pair (2-10,A) columns represent dealer's upcard (2-10,A)
        // Reminder 0=hit 1=stand 2=double 3=split 
        //TODO fill in correct strategy values as this was generated from memory
        int[][] pairStrategyTable = {
            // Dealer's upcard: 2 3 4 5 6 7 8 9 10 A
            /*2*/ {3,3,3,3,3,3,0,0,0,0},
            /*3*/ {3,3,3,3,3,3,0,0,0,0},
            /*4*/ {0,0,0,3,3,0,0,0,0,0},
            /*5*/ {2,2,2,2,2,2,2,2,0,0},
            /*6*/ {3,3,3,3,3,0,0,0,0,0},
            /*7*/ {3,3,3,3,3,3,0,0,1,1},
            /*8*/ {1,1,1,1,1,1,1,1,1,1},
            /*9*/ {3,3,3,3,3,1,3,3,1,1},
            /*10*/{1,1,1,1,1,1,1,1,1,1},
            /*A*/ {3,3,3,3,3,3,3,3,3,3}
        };
        // Return the action from the pair strategy table
        return pairStrategyTable[hand[0].getValue()-2][dealerCard.getValue()-2];
    }

    
    // Method to check soft hand strategy
    protected int checkSoftStrategy(Card dealerCard, Card[] hand) {
        // Table for soft hand strategy 
        // reminder 0=hit 1=stand 2=double 3=split(should not occur in soft strategy)
        //TODO fill in correct strategy values (Seriously need to get these from a real basic strategy chart)
        int[][] softStrategyTable = {
            // Dealer's upcard: 2 3 4 5 6 7 8 9 10 A
            /*13*/ {0,0,0,2,2,0,0,0,0,0},
            /*14*/ {0,0,0,2,2,0,0,0,0,0},
            /*15*/ {0,0,2,2,2,0,0,0,0,0},
            /*16*/ {0,0,2,2,2,0,0,0,0,0},
            /*17*/ {1,2,2,2,2,1,1,0,0,0},
            /*18*/ {1,1,1,1,1,1,1,1,0,0},
            /*19*/ {1,1,1,1,1,1,1,1,1,1},
            /*20*/ {1,1,1,1,1,1,1,1,1,1}
        };
        /*Return based on soft hand strategy table 
        Subtract 13 from hand score to get correct row and dealer card value -2 for correct column
        */
        return softStrategyTable[handScore - 13][dealerCard.getValue() - 2];
          }
    // Method to check hard hand strategy
    protected int checkHardStrategy(Card dealerCard, Card[] hand) {
        // Table for hard hand strategy Starts at 8 because the table only covers 8-20
        //TODO fill in correct strategy values (yes again)
        int[][] hardStrategyTable = {
            // Dealer's upcard: 2 3 4 5 6 7 8 9 10 A
            /*8*/  {0,0,0,0,0,0,0,0,0,0},
            /*9*/  {0,2,2,2,2,0,0,0,0,0},
            /*10*/ {2,2,2,2,2,2,2,2,0,0},
            /*11*/ {2,2,2,2,2,2,2,2,2,2},
            /*12*/ {0,0,1,1,1,0,0,0,0,0},
            /*13*/ {1,1,1,1,1,0,0,0,0,0},
            /*14*/ {1,1,1,1,1,0,0,0,0,0},
            /*15*/ {1,1,1,1,1,0,0,0,3,0},
            /*16*/ {1,1,1,1,1,0,0,3,3,0},
            /*17*/ {1,1,1,1,1,1,1,1,1,1},
            /*18*/ {1,1,1,1,1,1,1,1,1,1},
            /*19*/ {1,1,1,1,1,1,1,1,1,1},
            /*20*/ {1,1,1,1,1,1,1,1,1}
        
        };
          //Return based on hard hand strategy table subtract 8 from hand score to get correct row and dealer card value -2 for correct column
        return hardStrategyTable[handScore - 8][dealerCard.getValue() - 2];
          }


    protected boolean checkPair (Card[] hand) {
        // Placeholder implementation to check if hand is a pair
        return hand[0]== hand[1];      }



    protected boolean checksoft (Card[] hand) {
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

    public void  setHandScore(int score) {
        this.handScore = score;
    }
    public int getHandScore() {
        return this.handScore;
    }

    
}