package com.amaris.blackjack_simulation_project;
//import arrays for array manipulation
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//import collections for shuffling
import java.util.Collections;




import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


// Class to represent a blackjack table
public class Table {
    //Variables to hold game state
    // Array to hold dealers hand using "Plenty big" approach to avoid dynamic resizing
    Card[] dealerHand = new Card[12];
   
    //Array to hold a single deck of cards for loading the shoe 
    Card[] deck;
     //Array to hold "shoe" of cards (multiple decks)
    Card[] shoe;
    //Arraylist to hold the discard pile
    ArrayList<Card> discard;
    //Array to hold players at the table
    Player[] players;
    //Object to hold specfic table rules
    TableRules rules;
    //integer to keep track of where the cut card is to stop the shoe
    int cutCard=0;

    // constructor using default blackjack  house rules
    public Table() {
        this.rules= new TableRules();

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

    // Set the shoe size to number of decks* cards in a deck
    public void initalizeShoeSize(){
        this.shoe =  new Card[rules.getDeckNumber()*52];
    }
  
    //method to read a json file that contains all the cards in a standard deck
    
    public void loadDeck() {
        //location of cards config file make this selectable later
        String src = "src/main/resources/Cards.json";
        Card[] cards =null;
        try {
            File cardsFile = new File(src);
            ObjectMapper objectMapper = new ObjectMapper();
            cards =objectMapper.readValue(cardsFile, Card[].class);
           this.setDeck(cards);
        
        }
        catch(DatabindException d){
            d.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        
       }


    // load up the shoe based on max number of decks and cards in the "deck"
    public void loadShoe(){
        int shoeIndex=0;
			for (int i = 0; i < this.rules.getDeckNumber(); i++) {
				for(int k=0;k<52;k++){
                this.shoe[shoeIndex]=deck[k];
				shoeIndex++;}

				
			}
    }
    
    
    
    
    
    
    
    
    
    //Getters and setters banished to the near bottom like usual 

    //Method to get dealer's upcard value
     public int getDealerScore() {
        //return The value of the dealer's upcard which should be the second card in dealerHand
        // (assuming first card is face down)
        return this.dealerHand[1].getValue();
        
    }

    public void addPlayer(Player player){
        this.players[0]=player;
    }
      public Card[] getShoe(){
        return this.shoe;
    }
    
    public void setDeck(Card[] deck) {
        this.deck = deck;
    }
    public Card[] getDeck() {
        return deck;
    }

   
     
        
    
    @Override
    public String toString(){
         StringBuilder shoeString = new StringBuilder("The shoe contains:\n");
         for (Card card  : shoe) {
            shoeString.append(card.toString()).append("\n");
         }

        return shoeString.toString();
    }
		
	}

    


