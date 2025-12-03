package com.amaris.blackjack_simulation_project;

//TODO change access modifiers so that subclasses can access necessary methods and variables




/*
Class to act as a "player"  holding the score of the hand  basic player will follow the basic strategy */
public class Player {
    //Integer to track current hand for splits
    protected  int currentHand =0; 
    //Array to hold current hand's cards for easier reading
    Card[] handCards;
    //Arrays to hold cards in hand multiple hands for splits for our each player will otherwise have only one hand
    //Default to max 4 hands with max 11 cards each (theoretical max in blackjack)
    protected  Hand[] hands;
    //Record of wins losses and pushes for player
    protected  int wins =0;
    protected  int losses =0;
    protected  int pushes =0; 
    //Integer to track hand score for strategy decisions
    protected  int handScore=0;
    //Copy of table rules so the players can make some choices 
    TableRules rules;
    
    //Default constructor Uses most common blackjack rules
    public Player() {
        // Make the default table rules 
        this.rules = new TableRules();
        //get max splits from the table rules add one to make total number of hands after splits
         int maxHands= rules.getMaxSplits()+1;
        //create array to hold max 4 hands
        hands = new Hand[maxHands];
        //remember to initialize each hand in the array
        for (int i=0; i<maxHands; i++) {
            hands[i] = new Hand();
        }


    }
    // Create player based on given table rules 
    public Player(TableRules rules) {
        //get max splits from the table rules add one to make total number of hands after splits
        int maxHands= rules.getMaxSplits()+1;
        //create array to hold specified number of hands based on maxHands parameter
        hands = new Hand[maxHands];
        //remember to initialize each hand in the array
        for (int i=0; i<maxHands; i++) {
            hands[i] = new Hand();  }

    }

    //Method to decide what action to take based on dealer's card and player's hand
    public int strategy(Card dealerCard, int currentHand) {
         // Int to hold "choice" of what to do 0=hit 1=stand 2=double 3=split default to hit
         int decision;
         //Array to hold current hand's cards for easier reading
          handCards = this.hands[currentHand].getCards();
        //Get current hand score
        int handScore = this.hands[currentHand].getScore();

        //Check to see if pair 
        if(handCards.length==2){
         hands[currentHand].setIsPair(hands[currentHand].checkPair(handCards));
       
        }
        
    
        //Check for soft hand
        hands[currentHand].setIsSoft(hands[currentHand].checksoft(handCards));
       

        if (hands[currentHand].getIsPair()) {
        //check against pair strategy table
        decision = checkPairStrategy(dealerCard, handCards);
       }
       else if (hands[currentHand].getIsSoft()) {
        //check against soft hand strategy table
        decision = checkSoftStrategy(dealerCard, handCards);  
       }
       else {
        //check against hard hand strategy table
        decision = checkHardStrategy(dealerCard, handCards);
       }
        // Return the decision
    
       return decision;
    }


    protected int checkPairStrategy(Card dealerCard, Card[] hand) {
        // Pair strategy table  rows represent player's pair (2-10,A) columns represent dealer's upcard (2-10,A)
        // Reminder 0=hit 1=stand 2=double 3=split 

        int[][] pairStrategyTableNoDouble = {
            // Dealer's upcard: 2 3 4 5 6 7 8 9 10 A
                         /*2*/ {0,0,3,3,3,3,0,0,0,0},
                         /*3*/ {0,0,3,3,3,3,0,0,0,0},
                         /*4*/ {0,0,0,0,0,0,0,0,0,0},
                         /*5*/ {2,2,2,2,2,2,2,2,0,0},
                         /*6*/ {0,3,3,3,3,0,0,0,0,0},
                         /*7*/ {3,3,3,3,3,3,0,0,0,0},
                         /*8*/ {3,3,3,3,3,3,3,3,3,3},
                         /*9*/ {3,3,3,3,3,1,3,3,1,1},
                         /*10*/{1,1,1,1,1,1,1,1,1,1},
                         /*A*/ {3,3,3,3,3,3,3,3,3,3}
        };
        int[][] pairStrategyTableDoubleAfterSplit = {
            // Dealer's upcard: 2 3 4 5 6 7 8 9 10 A
                         /*2*/ {3,3,3,3,3,3,0,0,0,0},
                         /*3*/ {3,3,3,3,3,3,0,0,0,0},
                         /*4*/ {0,0,0,3,3,0,0,0,0,0},
                         /*5*/ {2,2,2,2,2,2,2,2,0,0},
                         /*6*/ {3,3,3,3,3,0,0,0,0,0},
                         /*7*/ {3,3,3,3,3,3,0,0,1,1},
                         /*8*/ {3,3,3,3,3,3,3,3,3,3},
                         /*9*/ {3,3,3,3,3,1,3,3,1,1},
                         /*10*/{1,1,1,1,1,1,1,1,1,1},
                         /*A*/ {3,3,3,3,3,3,3,3,3,3}
        };
        if(this.rules.getDoubleAfterSplit())
            {return pairStrategyTableDoubleAfterSplit[hand[0].getValue()-2][dealerCard.getValue()-2];}
        else{
        // Return the action from the pair strategy table
        return pairStrategyTableNoDouble[hand[0].getValue()-2][dealerCard.getValue()-2];}
    }

    
    // Method to check soft hand strategy
    protected int checkSoftStrategy(Card dealerCard, Card[] hand) {
        // Table for soft hand strategy 
        // reminder 0=hit 1=stand 2=double 3=split(should not occur in soft strategy)
       
        int[][] softStrategyTable = {
            // Dealer's upcard:
            //      2 3 4 5 6 7 8 9 10 A
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
    
        if(handScore<8){
            return 0; //always hit under should be the only place this occurs 
        }
        int[][] hardStrategyTable = {
            // Dealer's upcard: 
            /*      2 3 4 5 6 7 8 9 10 A*/
            /*8*/  {0,0,0,0,0,0,0,0,0,0},
            /*9*/  {0,2,2,2,2,0,0,0,0,0},
            /*10*/ {2,2,2,2,2,2,2,2,0,0},
            /*11*/ {2,2,2,2,2,2,2,2,2,2},
            /*12*/ {0,0,1,1,1,0,0,0,0,0},
            /*13*/ {1,1,1,1,1,0,0,0,0,0},
            /*14*/ {1,1,1,1,1,0,0,0,0,0},
            /*15*/ {1,1,1,1,1,0,0,0,0,0},
            /*16*/ {1,1,1,1,1,0,0,0,0,0},
            /*17*/ {1,1,1,1,1,1,1,1,1,1},
            /*18*/ {1,1,1,1,1,1,1,1,1,1},
            /*19*/ {1,1,1,1,1,1,1,1,1,1},
            /*20*/ {1,1,1,1,1,1,1,1,1}
        
        };
          //Return based on hard hand strategy table subtract 8 from hand score to get correct row and dealer card value -2 for correct column
        return hardStrategyTable[handScore - 8][dealerCard.getValue() - 2];
          }

    //Debug helper  method to add cards to hand and see other functions work properly
    public void debugSetHand(Card[] hand) {
        //add cards to first hand only for testing
        this.hands[currentHand].setCards(hand);
        System.out.println(this.hands[currentHand].toString());

        
    }


    
}