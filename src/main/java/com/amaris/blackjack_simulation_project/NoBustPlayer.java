package com.amaris.blackjack_simulation_project;
//Class for a player that follows a no-bust strategy (yes, they exist ive seen them)
public class NoBustPlayer extends Player {



    @Override
    protected int checkHardStrategy(Card dealerCard, Card[] hand) {
        //No bust players will not hit on hands 12 and above
        if (this.getHandScore() >=12) {
            return 1; //stand
        }
        //Otherwise follow basic hard strategy(table is a bit overkill for this but whatever keeps things consistent)
        int[][] hardStrategyTable = {
            // Dealer's upcard: 2 3 4 5 6 7 8 9 10 A
            /*5*/ {0,0,0,0,0,0,0,0,0,0},
            /*6*/ {0,0,0,0,0,0,0,0,0,0},
            /*7*/ {0,0,0,0,0,0,0,0,0,0},
            /*8*/ {0,0,0,0,0,0,0,0,0,0},
            /*9*/ {2,2,2,2,2,0,0,0,0,0},
            /*10*/{2,2,2,2,2,2,2,2,0,0},
            /*11*/{2,2,2,2,2,2,2,2,2,2},
        };
        //Return based on hard hand strategy table
        return hardStrategyTable[this.getHandScore() - 5][dealerCard.getValue() - 2];
       
    }
    //Soft strategy not needed for no-bust player since they will never have a soft hand over 11
    //Pair strategy follows normal rules as no-bust player can still split
    //Double strategy follows normal rules as no-bust 

    
    
}