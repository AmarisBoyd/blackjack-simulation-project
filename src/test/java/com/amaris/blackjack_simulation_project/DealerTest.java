package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DealerTest {
    Table testTable;
    Player testPlayer;

    public static int[][] strategyValues() {
        //Array values Number to check, Expected result when hit on soft 17, Expected result when stay on soft 17
        // zero indicates hit and one indicates stay
        return new int[][]{new int[]{17, 0, 1},
                new int[]{16, 0, 0},
                new int[]{21, 1, 1},
                new int[]{18, 0, 1}};
    }

    public static Object[][] checkStateValuesNoSplit() {
        // String to hold the test inputs
        // format [Test name]:[player hand cards]:[dealer hand cards]:[expected wins]:[expected losses]:[expected pushes]
        String testInputs = "Player stays Dealer wins:10,7:10,9:0:1:0;"
                + "Player stays Player wins:10,11:10,10:1:0:0;"
                + "Push:10,10:10,10:0:0:1;" +
                "Player bust:10,5,8:10,10:0:1:0;" +
                "Dealer bust:10,7,:10,5,8:1:0:0";
        // Hand to hold the players cards
        Hand playerHand = new Hand();
        // hand to hold the dealers cards
        Hand dealerHand = new Hand();
        String testName = "";
        // Integer to store number of expected wins
        int expectedWins = 0;
        // Integer to store number of expected losses
        int expectedLosses = 0;
        // Integer to store number of expected pushes
        int expectedPushes = 0;

        //create an array of strings with the first parse of the test inputs
        String[] firstParseInputs = testInputs.split(";");

        // set the number of rows in the object array equal to the size of the first parse array
        Object[][] dealerActionValues = new Object[firstParseInputs.length][4];
        for (int k = 0; k < firstParseInputs.length; k++) {
            //Parse each string in the new array a second time to get individual values
            String[] secondParseInputs = firstParseInputs[k].split(":");
            //loop over the second array of strings that is formed
            for (int i = 0; i < secondParseInputs.length; i++) {
                testName = secondParseInputs[0];
                //set expected wins to second parses 4th value
                expectedWins = Integer.parseInt(secondParseInputs[3]);
                //set expected losses to the second parses 5th value
                expectedLosses = Integer.parseInt(secondParseInputs[4]);
                //set expected pushes to the second parses 6th value
                expectedPushes = Integer.parseInt(secondParseInputs[5]);
                // run a third parse to get the individual cards to add
                String[] thirdParseInputs = secondParseInputs[i].split(",");
                //loop over the third array
                for (String thirdParseInput : thirdParseInputs) {
                    // if we are in the first value of the second array add the card to the player hand
                    if (i == 1) {
                        playerHand.addCard(new Card(Integer.parseInt(thirdParseInput)));
                    }
                    //if we are in the second value of the second array add the card to the dealer hand
                    if (i == 2) {
                        dealerHand.addCard(new Card(Integer.parseInt(thirdParseInput)));
                    }
                }

            }

            //Add the now parsed values to a new object and store it in row "k" of the object array
            dealerActionValues[k] = new Object[]{testName, new Hand(playerHand), new Hand(dealerHand), expectedWins, expectedLosses, expectedPushes};
            //reset player hand and dealer hand so the next time we do the loop we have an empty hand
            playerHand = new Hand();
            dealerHand = new Hand();

        }
        return dealerActionValues;
    }

    public static Object[][] checkStateSplitValues() {
        // String to hold the test inputs
        // format Test name: Mock Shoe: expected wins: expected losses: expected pushes
        String testInputs = "Player has win and a loss:11,9,8,8,10,8:1:1:0;"
                + "Both hands loose:11,9,10,8,10,8:0:2:0;"
                + "Both hands push :10,10,10,8,8,8:0:0:2;" +
                "Player bust both hands :10,4,10,4,10,8,10,8:0:2:0;" +
                "Player bust one hand Dealer wins:10,4,8,3,10,8,10,8:0:2:0;" +
                "Player bust one hand Dealer Loses:10,4,8,3,7,8,10,8:1:1:0;" +
                "Player bust one hand Dealer push:10,4,6,3,7,8,10,8:0:1:1;" +
                "Player bust one hand Dealer bust:10,10,4,8,3,5,8,10,8:1:1:0;" +
                "Dealer bust both hands win:10,8,4,8,3,5,8,10,8:2:0:0";
        ArrayList<Card> mockShoe = new ArrayList<>();
        String testName;
        // Integer to store number of expected wins
        int expectedWins;
        // Integer to store number of expected losses
        int expectedLosses;
        // Integer to store number of expected pushes
        int expectedPushes;

        //create an array of strings with the first parse of the test inputs
        String[] firstParseInputs = testInputs.split(";");

        // set the number of rows in the object array equal to the size of the first parse array
        Object[][] dealerActionValues = new Object[firstParseInputs.length][4];
        for (int k = 0; k < firstParseInputs.length; k++) {
            //Parse each string in the new array a second time to get individual values
            String[] secondParseInputs = firstParseInputs[k].split(":");

            testName = secondParseInputs[0];
            //set expected wins to second parses 3rd value
            expectedWins = Integer.parseInt(secondParseInputs[2]);
            //set expected losses to the second parses 4th value
            expectedLosses = Integer.parseInt(secondParseInputs[3]);
            //set expected pushes to the second parses 5th value
            expectedPushes = Integer.parseInt(secondParseInputs[4]);
            // run a third parse to get the individual cards to add to the shoe
            String[] thirdParseInputs = secondParseInputs[1].split(",");
            for (String thirdParseInput : thirdParseInputs) {
                mockShoe.add(new Card(Integer.parseInt(thirdParseInput)));
            }


            //Add the now parsed values to a new object and store it in row "k" of the object array
            dealerActionValues[k] = new Object[]{testName, mockShoe.clone(), expectedWins, expectedLosses, expectedPushes};
            mockShoe.clear();


        }
        return dealerActionValues;
    }

    public static Object[][] cleanTableValues() {
        // String to hold the test inputs
        // format Test name: Mock Shoe: expected wins: expected losses: expected pushes
        String testInputs = "Player has win and a loss:11,9,8,8,10,8:8,10,8,8,9,11;"
//                + "Both hands loose:11,9,10,8,10;"
//                + "Both hands push :10,10,10,8,8,8;" +
//                "Player bust both hands :10,4,10,4,10,8,10,8;" +
//                "Player bust one hand Dealer wins:10,4,8,3,10,8,10;" +
//                "Player bust one hand Dealer Loses:10,4,8,3,7,8,10,8;" +
//                "Player bust one hand Dealer push:10,4,6,3,7,8,10,8;"+
//                "Player bust one hand Dealer bust:10,10,4,8,3,5,8,10,8;" +
//                "Dealer bust both hands win:10,8,4,8,3,5,8,10,8"
                ;
        ArrayList<Card> mockShoe = new ArrayList<>();
        String testName;
        ArrayList<Card> mockDiscard = new ArrayList<>();
        //create an array of strings with the first parse of the test inputs
        String[] firstParseInputs = testInputs.split(";");

        // set the number of rows in the object array equal to the size of the first parse array
        Object[][] dealerActionValues = new Object[firstParseInputs.length][4];
        for (int k = 0; k < firstParseInputs.length; k++) {
            //Parse each string in the new array a second time to get individual values
            String[] secondParseInputs = firstParseInputs[k].split(":");
            testName = secondParseInputs[0];
            String[] thirdParseInputs = secondParseInputs[1].split(",");
            for (String thirdParseInput : thirdParseInputs) {
                mockShoe.add(new Card(Integer.parseInt(thirdParseInput)));
            }
            String[] fourthParseInputs = secondParseInputs[2].split(",");
            for (String fourthParseInput : fourthParseInputs) {
                mockDiscard.add(new Card(Integer.parseInt(fourthParseInput)));
            }


            //Add the now parsed values to a new object and store it in row "k" of the object array
            dealerActionValues[k] = new Object[]{testName, mockShoe.clone(), mockDiscard.clone()};
            mockDiscard.clear();
            mockShoe.clear();


        }
        return dealerActionValues;
    }

    @BeforeEach
    public void setup() {
        testTable = new Table();

        testPlayer = new Player();
        testTable.addPlayer(testPlayer);
    }

    @Test
    void dealCard_Score_UpDated_Hand_Not_Null_Test() {
        Dealer testDealer = testTable.getDealer();

        ArrayList<Card> mockShoe = new ArrayList<>();
        mockShoe.add(new Card(10));

        assertNotNull(testDealer.getDealerHand());
        testDealer.dealCard(mockShoe);
        assertEquals(10, testDealer.getDealerHand().getScore());

    }

    @ParameterizedTest
    @MethodSource("strategyValues")
    void strategy_Test(int[] values) {
        int numberToCheck = values[0];
        int hitSoft17Results = values[1];
        int staySoft17Results = values[2];
        Dealer testDealer = testTable.getDealer();
        //set the score to the value
        testDealer.getDealerHand().setIsSoft(true);
        testDealer.getDealerHand().setScore(numberToCheck);
        assertEquals(hitSoft17Results, testDealer.strategy());

        testDealer.getTableRules().setDealerHitsSoft17(false);
        assertEquals(staySoft17Results, testDealer.strategy());


    }

    @Test
    void checkBust_One_Ace_No_Bust_Test() {
        Dealer dealer = new Dealer();
        Hand testHand = new Hand();
        for (int i : new int[]{11, 5, 10}) {
            testHand.addCard(new Card(i));
        }
        dealer.checkBust(testHand);
        assertEquals(16, testHand.getScore());

    }

    @Test
        //this type of situation shouldn't exist because you add to the hand one card at a time
        // so after the first 10 the ace would be a 1
    void checkBust_OneAce_No_Bust_Test() {
        Dealer dealer = new Dealer();
        Hand testHand = new Hand();
        for (int i : new int[]{11, 5, 10, 10}) {
            testHand.addCard(new Card(i));
        }
        assertTrue(dealer.checkBust(testHand));
        assertEquals(26, testHand.getScore());
    }

    @Test
    void checkBust_Two_Ace_No_Bust_Test() {
        Dealer dealer = new Dealer();
        Hand testHand = new Hand();
        for (int i : new int[]{11, 11, 7}) {
            testHand.addCard(new Card(i));
        }
        assertFalse(dealer.checkBust(testHand));
        assertEquals(19, testHand.getScore());


    }

    @Test
        //this type of situation shouldn't exist because you add to the hand one card at a time
        // so after the 6 the first ace would be a one
        // Still checking for sanity purposes
    void checkBust_Two_Ace_Bust_Test() {
        Dealer dealer = new Dealer();
        Hand testHand = new Hand();
        for (int i : new int[]{11, 11, 6, 10}) {
            testHand.addCard(new Card(i));
        }

        // assertTrue(dealer.checkBust(testHand));
        //assertEquals(18, testHand.getScore());
    }

    @ParameterizedTest
    @MethodSource("checkStateValuesNoSplit")
    void checkState_No_Split_Test(String testName, Hand playerHand, Hand dealerHand, int expectedWins, int expectedLosses, int expectedPushes) {

        int[] expectedResults = {expectedWins, expectedLosses, expectedPushes};
        int[] actualResults;
        Player tempPlayer = this.testTable.getPlayers()[0];
        Dealer tempDealer = this.testTable.getDealer();
        tempPlayer.debugSetHand(playerHand.getCards());
        tempDealer.setDealerHand(dealerHand);
        // check if the player has bust
        if (tempDealer.checkBust(tempPlayer.getHand()[0])) {
            //if they have set that flag as it wouldn't be set since we added the cards directly
            tempPlayer.setHasBust(true);
        }
        tempDealer.checkTableState(testTable.getPlayers(), 1);
        //get the results of the check
        actualResults = new int[]{testTable.getPlayers()[0].getWins(),
                testTable.getPlayers()[0].getLosses(),
                testTable.getPlayers()[0].getPushes()};
        //check them against the expected results
        assertArrayEquals(expectedResults, actualResults);


    }

    @ParameterizedTest
    @MethodSource("checkStateSplitValues")
    void check_State_Split_Test(String testName, ArrayList<Card> mockShoe, int expectedWins, int expectedLosses, int expectedPushes) {
        int[] actualResults;
        int[] expectedResults = {expectedWins, expectedLosses, expectedPushes};


        testTable.setShoe(mockShoe);

        testTable.dealInitialCards();
        testTable.playerTurn();
        testTable.dealerTurn();
        testTable.getDealer().checkTableState(testTable.getPlayers(), 1);
        actualResults = new int[]{testTable.getPlayers()[0].getWins(), testTable.getPlayers()[0].getLosses(), testTable.getPlayers()[0].getPushes()};
        assertArrayEquals(expectedResults, actualResults);
    }

    @ParameterizedTest
    @MethodSource("cleanTableValues")
    void cleanTable_Hands_empty_Test(String testName, ArrayList<Card> mockShoe, ArrayList<Card> expectedDiscard) {
        //create test dealer for readability of debug
        Dealer testDealer = this.testTable.getDealer();
        //create test player to make reading debug easier
        Player testPlayer = testTable.getPlayers()[0];
        //set the shoe to the mock shoe
        testTable.setShoe(mockShoe);
        //deal the first cards
        testTable.dealInitialCards();
        //do player action
        testTable.playerTurn();
        //do dealer actions
        testTable.dealerTurn();
        //check the player state so wins and losses are updated
        testDealer.checkTableState(testTable.getPlayers(), 1);
        //try to clean the table
        testDealer.cleanTable(testTable.getPlayers(), testTable.getPlayerCount(), testTable.getDiscard());

        assertFalse(testPlayer.isHasBust());
        assertFalse(testPlayer.hasSplitAces());
        assertFalse(testPlayer.isHasSplit());
        //assert that discard looks how we want it to
        String testDiscard = testTable.getDiscardToString();
        testTable.setDiscard(expectedDiscard);
        String expectedDiscardToString = testTable.getDiscardToString();
        // assertEquals(expectedDiscardToString, testDiscard);

    }


}
