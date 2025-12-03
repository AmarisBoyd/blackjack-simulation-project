package com.amaris.blackjack_simulation_project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlackjackSimulationProjectApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	 static void test_pair_strategy(){
	// Test Player strategy method
		//Add cards to dealer
		Card dealerCard = new Card("8S",8);
		//create test hand for player
		Card[] testHand = {new Card("8 of Hearts",8), new Card("8 Of Diamonds",8)};
		//Create player
		Player player = new Player();
		//Set player's hand for testing
		

		assertEquals(2, player.checkPairStrategy(dealerCard,testHand));
	}
	@Test
	 static void test_Hard_Strategy(){
		//Set player's hand for testing
		Card dealerCard = new Card("8 of spades",8);
		//create test hand for player
		Card[] testHand = {new Card("10 of Hearts",10), new Card("8 Of Diamonds",8)};
		//Create player
		Player player = new Player();
		//Set player's hand for testing
		player.debugSetHand(testHand);
		//Get strategy decision and print to console
		assertEquals(1,player.checkHardStrategy(dealerCard, testHand));

	}

}
