package com.amaris.blackjack_simulation_project;
//import arrays for array manipulation
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
//import collections for shuffling
import java.util.Collections;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


// Class to represent a blackjack table
public class Table {
    //Variables to hold game state
    // Array to hold dealers hand using "Plenty big" approach to avoid dynamic resizing 
    Card[] dealerHand = new Card[12];
    //Array to hold "shoe" of cards (multiple decks)
    Card[] shoe;
    //Array to hold players at the table
    Player[] players;
    TableRules rules;
    // constructor using default blackjack  house rules
    public Table() {
        this.rules= new TableRules();

    }

    

    //Method to get dealer's upcard value
    public int getDealerScore() {
        //return The value of the dealer's upcard which should be the second card in dealerHand
        // (assuming first card is face down)
        return this.dealerHand[1].getValue();
        
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
    //method to read a json file that contains all the cards in a standard deck
    //TODO fix this!! non functional read up on reading from JSON files again 
    public void loadDeck() throws  DatabindException, IOException{
        String src = "src/main/resources/Cards.json";
        File file = new File(src);
		Card[] deckCards = new Card[52];
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(file);
        String suit = node.get("suit").asText();
        String rank = node.get("rank").asText();
        int value = node.get("numeric_value").asInt();
        System.out.println("Suit"+ suit);
        System.out.println("Rank"+rank);
        System.out.println("Value"+value);

		
	}

    


}