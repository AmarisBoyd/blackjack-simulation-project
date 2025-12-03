package com.amaris.blackjack_simulation_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlackjackSimulationProjectApplicationTests {


	@Test
	void test_pair_strategy(){
	// Test Player strategy method
		//Add cards to dealer
		Card dealerCard = new Card("8S",8);
		//create test hand for player
		Card[] testHand = {new Card("8 of Hearts",8), new Card("8 Of Diamonds",8)};
		//Create player
		Player player = new Player();
		//Set player's hand for testing
		player.handCards=testHand;
		assertNotNull(player);
		player.calcHandScore();
		assertNotEquals(0,player.handScore);
		assertEquals(16,player.handScore);
		assertEquals(3, player.checkPairStrategy(dealerCard,player.handCards));
	}
	@Test
	 void test_Hard_Strategy(){
		//Set player's hand for testing
		Card dealerCard = new Card("8 of spades",8);
		//create test hand for player
		Card[] testHand = {new Card("10 of Hearts",10), new Card("8 Of Diamonds",8)};
		//Create player
		Player player = new Player();
		//Set player's hand for testing

		player.handCards=testHand;
		assertNotNull(player.handCards);
		player.calcHandScore();
		assertNotEquals(0, player.handScore);
		//Get strategy decision and print to console
		
		assertEquals(1,player.checkHardStrategy(dealerCard, testHand));
		
	}

}
