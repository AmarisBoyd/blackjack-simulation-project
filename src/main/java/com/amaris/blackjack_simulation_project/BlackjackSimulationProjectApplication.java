package com.amaris.blackjack_simulation_project;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.DatabindException;

@SpringBootApplication
public class BlackjackSimulationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackSimulationProjectApplication.class, args);
		//Simple test of strategy
		testStrategy();
	}
	//method to test strategy with sample data

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
