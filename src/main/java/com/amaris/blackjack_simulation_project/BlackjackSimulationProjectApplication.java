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
	
	

}
