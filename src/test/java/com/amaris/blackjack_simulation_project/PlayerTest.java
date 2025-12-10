package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class PlayerTest {
    Card dealerCard;
    void test_helper(int[] playerCards, int DealerValue,Player player) {
        dealerCard = new Card("wildcard", DealerValue);
        Card playerCard1 = new Card("wildcard", playerCards[0]);
        Card playerCard2 = new Card("wildcard", playerCards[1]);
        player.handCards= new Card[]{playerCard1, playerCard2};
        player.calcHandScore();

    }

    /*
    Test of pair strategy
    TODO: Add more edge cases
     */
    @Test
    void test_pair_strategy_8s_on_8(){
        Player player = new Player();
        test_helper(new int[]{8, 8},8,player);
        assertNotEquals(0,player.handScore);
        assertNotEquals(0,player.handScore);
        assertEquals(16,player.handScore);
        assertEquals(3, player.checkPairStrategy(dealerCard,player.handCards));
    }
    @Test
    void test_pair_strategy_9s_on_9(){
        //Create player
        Player player = new Player();
        test_helper(new int[]{9, 9},9,player);
        assertNotEquals(0,player.handScore);
        assertEquals(3, player.checkPairStrategy(dealerCard,player.handCards));

    }
	/*
	Test of hard strategy
	TODO: Test the cases on the edge of the array
	 */

    @Test
    void test_Hard_Strategy_18_on_8(){
        Player player = new Player();
        test_helper(new int[]{10, 8},8,player);
        assertNotEquals(0,player.handScore);
        assertNotNull(player.handCards);
        assertNotEquals(0, player.handScore);
        assertEquals(18,player.handScore);
        assertEquals(1,player.checkHardStrategy(dealerCard, player.handCards));

    }
    @Test
    void test_Hard_Strategy_Under_8(){
        Player player = new Player();
        test_helper(new int[]{4, 3},9,player);
        assertEquals(0,player.checkHardStrategy(dealerCard, player.handCards));
    }


	/*
	Test soft strategy
	 */

    @Test
    void test_Soft_strategy_18_on_10(){
        Player player = new Player();
        test_helper(new int[]{7, 11},10,player);
        assertNotEquals(0,player.handScore);
        assertNotEquals(0, player.handScore);

        assertEquals(18,player.handScore);
        assertEquals(0,player.checkSoftStrategy(dealerCard, player.handCards));

    }
    @Test
    void test_Soft_17_strategy(){
        Player player = new Player();
        test_helper(new int[]{6, 11},10,player);
        assertNotEquals(0,player.handScore);
        assertNotEquals(0, player.handScore);

        assertEquals(17,player.handScore);
        assertEquals(0,player.checkSoftStrategy(dealerCard, player.handCards));

    }
}
