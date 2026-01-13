package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest


public class PlayerTest {

    Card dealerCard;
    Card[] playerCards;

    void test_helper(int[] playerCards, int DealerValue, Player player) {
        dealerCard = new Card("wildcard", DealerValue);
        Card playerCard1 = new Card("wildcard", playerCards[0]);
        Card playerCard2 = new Card("wildcard", playerCards[1]);
        player.handCards = new Card[]{playerCard1, playerCard2};
        player.calcHandScore();

    }
    private static Object[][] arrayFill(int[] col1, int[]col10,int[] row1){

        Object[][] values = new Object[40][3];

        int k = 0;
        for (int i = 2; i < 12; i++) {
            values[k] = new Object[]{
                    new Card(2),
                    new Card[]{new Card(i),
                            new Card(i)},
                    col1[i - 2]};
            k++;
        }
        for (int i = 2; i < 12; i++) {
            values[k] = new Object[]{
                    new Card(11),
                    new Card[]{new Card(i),
                            new Card(i)},
                    col10[i - 2]};
            k++;
        }
        for (int i = 2; i < 12; i++) {
            values[k] = new Object[]{
                    new Card(i),
                    new Card[]{new Card(2),
                            new Card(2)},
                    row1[i - 2]};
            k++;
        }
        for (int i = 2; i < 12; i++) {
            values[k] = new Object[]{
                    new Card(i),
                    new Card[]{new Card(11),
                            new Card(11)},
                    3};
            k++;
        }
        return values;
    }

    /*
    Test of pair strategy
     */
    private static Object[][] pairNoDoubleValues() {
        int[] expectedValuesCol1 = {0, 0, 0, 2, 0, 3, 3, 3, 1, 3};
        int[] expectedValuesCol10 = {0, 0, 0, 0, 0, 0, 3, 1, 1, 3};
        int[] expectedValuesRow1 = {0, 0, 3, 3, 3, 3, 0, 0, 0, 0};


        return arrayFill(expectedValuesCol1,expectedValuesCol10,expectedValuesRow1);
    }

    private static Object[][] pairDoubleAfterSplitValues() {

        int[] expectedValuesCol1 = {3, 3, 0, 2, 3, 3, 3, 3, 1, 3};
        int[] expectedValuesCol10 = {0, 0, 0, 0, 0, 1, 3, 1, 1, 3};
        int[] expectedValuesRow1 = {3, 3, 3, 3, 3, 3, 0, 0, 0, 0};
        return arrayFill(expectedValuesCol1,expectedValuesCol10,expectedValuesRow1);
    }


    @ParameterizedTest
    @MethodSource("pairDoubleAfterSplitValues")
    void testDoublePairs(Card dealerCard, Card[] playerCards, int expectedValue) {
        Player player = new Player();
        assertEquals(expectedValue, player.checkPairStrategy(dealerCard, playerCards));
    }

    @ParameterizedTest
    @MethodSource("pairNoDoubleValues")
    void testNoDoublePairs(Card dealerCard, Card[] playerCards, int expectedValue) {
        Player player = new Player();
        player.rules.setDoubleAfterSplitAllowed(false);
        assertEquals(expectedValue, player.checkPairStrategy(dealerCard, playerCards));

    }


    @Test
    void test_Hard_Strategy_18_on_8() {
        Player player = new Player();
        test_helper(new int[]{10, 8}, 8, player);
        assertNotEquals(0, player.handScore);
        assertNotNull(player.handCards);
        assertNotEquals(0, player.handScore);
        assertEquals(18, player.handScore);
        assertEquals(1, player.checkHardStrategy(dealerCard, player.handCards));

    }

    @Test
    void test_Hard_Strategy_Under_8() {
        Player player = new Player();
        test_helper(new int[]{4, 3}, 9, player);
        assertEquals(0, player.checkHardStrategy(dealerCard, player.handCards));
    }


	/*
	Test soft strategy
	 */

    @Test
    void test_Soft_strategy_18_on_10() {
        Player player = new Player();
        test_helper(new int[]{7, 11}, 10, player);
        assertNotEquals(0, player.handScore);
        assertNotEquals(0, player.handScore);

        assertEquals(18, player.handScore);
        assertEquals(0, player.checkSoftStrategy(dealerCard, player.handCards));

    }

    @Test
    void test_Soft_17_strategy() {
        Player player = new Player();
        test_helper(new int[]{6, 11}, 10, player);
        assertNotEquals(0, player.handScore);
        assertNotEquals(0, player.handScore);

        assertEquals(17, player.handScore);
        assertEquals(0, player.checkSoftStrategy(dealerCard, player.handCards));

    }
}
