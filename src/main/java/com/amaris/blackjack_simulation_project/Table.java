package com.amaris.blackjack_simulation_project;
//import arrays for array manipulation
import java.util.Arrays;
//import collections for shuffling
import java.util.Collections;


// Class to represent a blackjack table
public class Table {
    //Variables to hold game state
    // Array to hold dealers hand using "Plenty big" approach to avoid dynamic resizing 
    Card[] dealerHand = new Card[12];
    //Array to hold "shoe" of cards (multiple decks)
    Card[] shoe;
    //Array to hold players at the table
    Player[] players;
    

    //Variables to hold table rules/settings
    //integer to hold max number of players at the table defaults to 6
    int maxPlayers=6;
    //integer to hold number of decks in shoe defaults to 6
    int numDecks=6;
    //integer to hold max number of splits allowed defaults to 3(for total of 4 hands)
    int maxSplits=3;
    //bolean to track if dealer hits on soft 17 defaults to true
    Boolean dealerHitsSoft17 = true;
    //bolean to track if surrender is allowed defaults to false
    Boolean surrenderAllowed = false;
    //Boolean to check if doubling after split is allowed defaults to true
    Boolean doubleAfterSplitAllowed = true;
    //boolean to track if resplitting aces is allowed defaults to false
    Boolean resplitAcesAllowed = false;
    //boolean to track if hitting split aces is allowed defaults to false
    Boolean hitSplitAcesAllowed = false;


    //Method to get dealer's upcard value
    public int getDealerScore() {
        //return The value of the dealer's upcard which should be the second card in dealerHand
        // (assuming first card is face down)
        this.dealerHand[1].getValue();
        return 0;
    }
    public void dealInitialCards() {
        //Deal two cards to each player and dealer from the shoe
        // Placeholder for loop through players and dealer to assign cards from shoe array
    }
    public void playerActions() {
        //Loop through each player and have them make decisions based on their strategy
        // Placeholder implementation probably a for loop through players array and a while loop for each player's hand
    }
    public void dealerActions() {
        //Have dealer play out their hand according to standard blackjack rules
        // Placeholder implementation while loop until dealer's hand value is 17 or higher or busts

        }
    public void shuffleShoe() {
        //Shuffle the shoe of cards
        Collections.shuffle(Arrays.asList(this.shoe));
        // Placeholder implementation could use a simple random shuffle algorithm
    }
    public void cutShoe(int cutPosition) {
        //Create new shoe array to hold cut shoe
        Card[] newShoe = new Card[this.shoe.length]; 
        //Copy the end of the shoe from cutPosition to end into new shoe
        newShoe=Arrays.copyOfRange(this.shoe, cutPosition, this.shoe.length);
        //Append the start of the shoe to the end of the new shoe
        newShoe = Arrays.copyOfRange(this.shoe, 0, cutPosition);
        //Return the now cut shoe
        this.shoe= newShoe;

    }

    //Getter to get collective table settings converting booleans to integers for easier handling 0 is false 1 is true
    public int[] getTableSettings() {
        /* integer array to hold settings in the order maxPlayers,
        numDecks, maxSplits, dealerHitsSoft17, surrenderAllowed, 
        doubleAfterSplitAllowed, resplitAcesAllowed, hitSplitAcesAllowed */
        int[] settings = new int[8];
        for (int i=0; i<8; i++) {
            switch(i) {
                case 0:
                    settings[i] = this.maxPlayers;
                    break;
                case 1:
                    settings[i] = this.numDecks;
                    break;
                case 2:
                    settings[i] = this.maxSplits;
                    break;
                case 3:
                    settings[i] = this.dealerHitsSoft17 ? 1 : 0;
                    break;
                case 4:
                    settings[i] = this.surrenderAllowed ? 1 : 0;
                    break;
                case 5:
                    settings[i] = this.doubleAfterSplitAllowed ? 1 : 0;
                    break;
                case 6:
                    settings[i] = this.resplitAcesAllowed ? 1 : 0;
                    break;
                case 7:
                    settings[i] = this.hitSplitAcesAllowed ? 1 : 0;
                    break;
                default:
                    break;
            }
        }
        //Return an array of integers representing table settings
        // Placeholder implementation returning maxPlayers and numDecks
        return settings;

        
    }   
    //Collection of setter methods for table settings
    public void setMaxPlayers(int maxPlayers) {
        //Set max players at the table usually between 1 and 7
        this.maxPlayers = maxPlayers;     }

    public void setNumDecks(int numDecks) {
        //set number of decks in shoe usually between 1 and 8 does change odds 
        this.numDecks = numDecks;     }
    public void setMaxSplits(int maxSplits) {
        //set max number of splits allowed usually 3(for total of 4 hands) 
        this.maxSplits = maxSplits;    }
    public void setDealerHitsSoft17(Boolean dealerHitsSoft17) {
        //set if dealer hits on soft 17 usually true
        this.dealerHitsSoft17 = dealerHitsSoft17;     }
    public void setSurrenderAllowed(Boolean surrenderAllowed) { 
        //set if surrender is allowed usually false
        this.surrenderAllowed = surrenderAllowed;     } 
    //set if doubling after split is allowed usually true
    public void setDoubleAfterSplitAllowed(Boolean doubleAfterSplitAllowed) {
        this.doubleAfterSplitAllowed = doubleAfterSplitAllowed;     }
    //set if resplitting aces is allowed usually false
    public void setResplitAcesAllowed(Boolean resplitAcesAllowed) {
        this.resplitAcesAllowed = resplitAcesAllowed;     }
    //set if hitting split aces is allowed usually false
    public void setHitSplitAcesAllowed(Boolean hitSplitAcesAllowed) {
        this.hitSplitAcesAllowed = hitSplitAcesAllowed;     }   


    // Overloaded setter methods for table settings using integers for booleans for setup based on config files
    //TODO implement these overloaded setters
    


}