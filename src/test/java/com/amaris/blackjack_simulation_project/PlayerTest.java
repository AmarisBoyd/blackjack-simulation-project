package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.amaris.blackjack_simulation_project.BlackjackAction.HIT;
import static com.amaris.blackjack_simulation_project.BlackjackAction.STA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
//TODO Refactor all of this to make it more in line with how TableTest works and make it easier to test

public class PlayerTest {

    Card dealerCard;
    Card[] playerCards;

    private static Object[][] arrayFill(int[] col1, int[] col10, int[] row1) {

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


        return arrayFill(expectedValuesCol1, expectedValuesCol10, expectedValuesRow1);
    }

    private static Object[][] pairDoubleAfterSplitValues() {

        int[] expectedValuesCol1 = {3, 3, 0, 2, 3, 3, 3, 3, 1, 3};
        int[] expectedValuesCol10 = {0, 0, 0, 0, 0, 1, 3, 1, 1, 3};
        int[] expectedValuesRow1 = {3, 3, 3, 3, 3, 3, 0, 0, 0, 0};
        return arrayFill(expectedValuesCol1, expectedValuesCol10, expectedValuesRow1);
    }

    void testHelper(int[] playerCards, int DealerValue, Player player) {
        dealerCard = new Card("wildcard", DealerValue);
        Card playerCard1 = new Card("wildcard", playerCards[0]);
        Card playerCard2 = new Card("wildcard", playerCards[1]);
        player.handCards = new ArrayList<>(List.of(playerCard1, playerCard2));


    }

    @ParameterizedTest
    @MethodSource("pairDoubleAfterSplitValues")
    void checkPairStrategy_Double_After_Split_Test(Card dealerCard, Card[] playerCards, int expectedValue) {

        ArrayList<Card> cardsToDeal = new ArrayList<>();
        Collections.addAll(cardsToDeal, playerCards);
        Player player = new Player();
        for (int i = 0; i < playerCards.length; i++) {
            player.dealCard(cardsToDeal);
        }

        assertEquals(expectedValue, player.strategy.checkPairStrategy(dealerCard, player.getHand()[player.getHand().length - 1]));
    }

    @ParameterizedTest
    @MethodSource("pairNoDoubleValues")
    void checkPAirStrategy_No_Double_After_Split_Test(Card dealerCard, Card[] playerCards, int expectedValue) {
        ArrayList<Card> playerCardsList = new ArrayList<>();
        Collections.addAll(playerCardsList, playerCards);
        Player player = new Player();
        for (int i = 0; i < playerCardsList.size(); i++) {
            player.dealCard(playerCardsList);

        }
        player.rules.setDoubleAfterSplitAllowed(false);
        Hand currentHand = player.getHand()[player.getCurrentHand()];
        assertEquals(expectedValue, player.strategy.checkPairStrategy(dealerCard, currentHand));

    }


    @Test
    void Hard_Strategy_18_On_8_Test() {
        Player player = new Player();
        testHelper(new int[]{10, 8}, 8, player);
        assertNotEquals(0, player.getHand()[player.getCurrentHand()].getScore());
        int score = player.getHand()[player.getCurrentHand()].getScore();
        assertNotEquals(0, score);
        assertEquals(18, score);
        assertEquals(STA, player.strategy.checkHardStrategy(dealerCard, player.getHand()[player.getCurrentHand()]));

    }

    @Test
    void hard_Strategy_Under_8_Test() {
        Player player = new Player();
        testHelper(new int[]{4, 3}, 9, player);
        assertEquals(HIT, player.strategy.checkHardStrategy(dealerCard, player.getHand()[player.getCurrentHand()]));
    }


	/*
	Test soft strategy
	 */

    @Test
    void soft_Strategy_18_On_10_Test() {

        Player player = new Player();
        testHelper(new int[]{7, 11}, 10, player);
        int score = player.getHand()[player.getCurrentHand()].getScore();
        assertNotEquals(0, score);
        assertNotEquals(0, score);

        assertEquals(18, score);
        assertEquals(HIT, player.strategy.checkSoftStrategy(dealerCard, player.getHand()[player.getCurrentHand()]));

    }

    @Test
    void soft_17_Strategy_Test() {
        Player player = new Player();
        testHelper(new int[]{6, 11}, 10, player);
        int score = player.getHand()[player.getCurrentHand()].getScore();
        assertNotEquals(0, score);
        assertNotEquals(0, score);

        assertEquals(17, score);
        assertEquals(HIT, player.strategy.checkSoftStrategy(dealerCard, player.getHand()[player.getCurrentHand()]));

    }


}
