package com.amaris.blackjack_simulation_project.model;

public class TableRules {
    //Variables to hold table rules/settings
    //integer to hold max number of players at the table defaults to 6
    private int maxPlayers = 6;
    //integer to hold number of decks in shoe defaults to 6
    private int numDecks = 6;
    //integer to hold max number of splits allowed defaults to 3(for total of 4 hands)
    private int maxSplits = 3;
    //boolean to track if dealer hits on soft 17 defaults to true
    private Boolean dealerHitsSoft17 = true;
    //boolean to track if surrender is allowed defaults to false
    private Boolean surrenderAllowed = false;
    //Boolean to check if doubling after split is allowed defaults to true
    private Boolean doubleAfterSplitAllowed = true;
    //boolean to track if resplitting aces is allowed defaults to false
    private Boolean resplitAcesAllowed = false;
    //boolean to track if hitting split aces is allowed defaults to false Here for Spanish 21
    private Boolean hitSplitAces = false;

    //Getter to get collective table settings converting booleans to integers for easier handling 0 is false 1 is true
    public int[] getTableSettings() {
        /* integer array to hold settings in the order maxPlayers,
        numDecks, maxSplits, dealerHitsSoft17, surrenderAllowed, 
        doubleAfterSplitAllowed, resplitAcesAllowed, hitSplitAcesAllowed */
        int[] settings = new int[8];
        for (int i = 0; i < 8; i++) {
            switch (i) {
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
                    settings[i] = this.hitSplitAces ? 1 : 0;
                    break;
                default:
                    break;
            }
        }
        //Return an array of integers representing table settings
        // Placeholder implementation returning maxPlayers and numDecks
        return settings;


    }

    public void setNumDecks(int numDecks) {
        //set number of decks in shoe usually between 1 and 8 does change odds
        this.numDecks = numDecks;
    }

    public void setDealerHitsSoft17(Boolean dealerHitsSoft17) {
        //set if dealer hits on soft 17 usually true
        this.dealerHitsSoft17 = dealerHitsSoft17;
    }

    public void setSurrenderAllowed(Boolean surrenderAllowed) {
        //set if surrender is allowed usually false
        this.surrenderAllowed = surrenderAllowed;
    }

    //set if doubling after split is allowed usually true
    public void setDoubleAfterSplitAllowed(Boolean doubleAfterSplitAllowed) {
        this.doubleAfterSplitAllowed = doubleAfterSplitAllowed;
    }

    //set if resplitting aces is allowed usually false
    public void setResplitAcesAllowed(Boolean resplitAcesAllowed) {
        this.resplitAcesAllowed = resplitAcesAllowed;
    }

    //Individual getters for the variables
    //getter for max players
    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    //Collection of setter methods for table settings
    public void setMaxPlayers(int maxPlayers) {
        //Set max players at the table usually between 1 and 7
        this.maxPlayers = maxPlayers;
    }

    //getter for number of decks
    public int getDeckNumber() {
        return this.numDecks;
    }


    // Overloaded setter methods for table settings using integers for booleans for setup based on config files
    // implement these overloaded setters

    //getter for max splits
    public int getMaxSplits() {
        return this.maxSplits;
    }

    public void setMaxSplits(int maxSplits) {
        //set max number of splits allowed usually 3(for total of 4 hands)
        this.maxSplits = maxSplits;
    }

    //getter for dealer hits soft 17
    public boolean doesHitSoft17() {
        return this.dealerHitsSoft17;
    }

    //getter for if surrender is allowed
    public boolean canSurrender() {
        return this.surrenderAllowed;
    }

    // getter for if doubling after splits is allowed
    public boolean canDoubleAfterSplit() {
        return this.doubleAfterSplitAllowed;
    }

    //getter for if resplitting aces is allowed
    public boolean canResplitAces() {
        return this.resplitAcesAllowed;
    }

    //getter for hitting split aces
    public Boolean canHitSplitAces() {
        return hitSplitAces;
    }

    //set if hitting split aces is allowed usually false
    public void setHitSplitAces(Boolean hitSplitAces) {
        this.hitSplitAces = hitSplitAces;
    }

    @Override
    public String toString() {
        return "TableRules{" +
                "maxPlayers=" + maxPlayers +
                ", numDecks=" + numDecks +
                ", maxSplits=" + maxSplits +
                ", dealerHitsSoft17=" + dealerHitsSoft17 +
                ", surrenderAllowed=" + surrenderAllowed +
                ", doubleAfterSplitAllowed=" + doubleAfterSplitAllowed +
                ", resplitAcesAllowed=" + resplitAcesAllowed +
                ", hitSplitAcesAllowed=" + hitSplitAces +
                '}';
    }
}