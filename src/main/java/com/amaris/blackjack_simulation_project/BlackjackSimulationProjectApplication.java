package com.amaris.blackjack_simulation_project;

import java.awt.SystemTray;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class BlackjackSimulationProjectApplication {

	public static void main(String[] args) {
		//disabling spring application so i can get the core functionality running before i mess with that nonsense 
		//SpringApplication.run(BlackjackSimulationProjectApplication.class, args);
		onePlayerTest();
	
	}
	//method to test strategy with sample data
	public static void onePlayerTest(){
	Table table =new Table();
		String shoePath ="src/main/resources/Shoe.txt";
		String shuffledFile ="src/main/resources/Shuffled.txt";
		Path shoeFile = Path.of(shoePath);
		Path shuffledPath = Path.of(shuffledFile);
		try {
			// load the card data from the Cards.Json file
			table.loadDeck();
			// Initatlize how big the shoe is 
			table.initalizeShoeSize();
			// Load the shoe up with copies of the deck
			table.loadShoe();
			//Write current configuration of the shoe to a file for easier testing 
			Files.writeString(shoeFile,table.toString());
			//Shuffle the shoe before we start
			table.shuffleShoe();
			// Write results to another file to compare to make sure shuffle worked 
			Files.writeString(shuffledPath,table.toString());
			// create a player with default strategy
			Player firstPlayer = new Player();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void testStrategy(){
	// Test Player strategy method
		//Add cards to dealer
		Card dealerCard = new Card("8S",2);
		//create test hand for player
		Card[] testHand = {new Card("8 of Hearts",8), new Card("8 Of Diamonds",8)};
		//Create player
		Player player = new Player();
		//Set player's hand for testing
		player.debugSetHand(testHand);
		System.out.print("Dealer upcard value: " + dealerCard.getValue() + "\n");
		System.out.println("ready to get strategy decision:");
		System.out.print("reminder  0=hit 1=stand 2=double 3=split: ");
		//Get strategy decision and print to console
		System.out.println(player.strategy(dealerCard,0) + "\n");
	}
	//method to test strategy with provided data
	public static void testStrategy(Card dealerCard, Card[] playerCards ){
		//Set player's hand for testing
		int currentHandIndex=0;
		Player player = new Player();
		player.debugSetHand(playerCards);
		System.out.print("Dealer upcard value: " + dealerCard.getValue() + "\n");
		System.out.println("ready to get strategy decision:");
		System.out.println("reminder  0=hit 1=stand 2=double 3=split");
		//Get strategy decision and print to console
		System.out.print(player.strategy(dealerCard,currentHandIndex) + "\n");

	}

}
