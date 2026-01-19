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





    @BeforeEach
    public void setup() {
        testTable = new Table();

        testPlayer = new Player();
        testTable.addPlayer(testPlayer);
    }


    @Test
    void Test_check_bust_one_ace_no_bust() {
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
    void Test_check_bust_one_ace_bust() {
        Dealer dealer = new Dealer();
        Hand testHand = new Hand();
        for (int i : new int[]{11, 5, 10, 10}) {
            testHand.addCard(new Card(i));
        }
        assertTrue(dealer.checkBust(testHand));
        assertEquals(26, testHand.getScore());
    }

    @Test
    void Test_checkBust_two_ace_no_bust() {
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
    void Test_checkBust_two_ace_bust() {
        Dealer dealer = new Dealer();
        Hand testHand = new Hand();
        for (int i : new int[]{11, 11, 6, 10}) {
            testHand.addCard(new Card(i));
        }
        assertTrue(dealer.checkBust(testHand));
        assertEquals(18, testHand.getScore());
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

    @ParameterizedTest
    @MethodSource("checkStateValuesNoSplit")
    void Test_Check_State_No_Split(String testName, Hand playerHand, Hand dealerHand, int expectedWins, int expectedLosses, int expectedPushes) {

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

    public static Object[][] checkStateSplitValues() {
        // String to hold the test inputs
        // format Test name: Mock Shoe: expected wins: expected losses: expected pushes
        String testInputs = "Player has win and a loss:11,9,8,8,10,8:1:1:0;"
                + "Both hands loose:11,9,10,8,10,8,:0:2:0"
//                + "Both hands push :10,10:10,10:0:0:1;" +
//                "Player bust both hands :10,5,8:10,10:0:2:0;" +
//                "Player bust one hand Dealer wins:10,7,:10,5,8:1:0:0;" +
//                "Player bust one hand Dealer Loses:10,7,:10,5,8:1:0:0;" +
//                "Player bust one hand Dealer push:10,7,:10,5,8:1:0:0;"+
//                "Player bust one hand Dealer bust:10,7,:10,5,8:1:0:0;"
                ;
        ArrayList<Card> mockShoe = new ArrayList<>();
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

    @ParameterizedTest
    @MethodSource("checkStateSplitValues")
    void Test_Check_State_Split(String testName, ArrayList<Card> mockShoe, int expectedWins, int expectedLosses, int expectedPushes) {
        int[] actualResults;
        int[] expectedResults = {expectedWins, expectedLosses, expectedPushes};


        testTable.setShoe(mockShoe);

        testTable.dealInitialCards();
        testTable.playerActions();
        testTable.dealerActions();
        testTable.getDealer().checkTableState(testTable.getPlayers(), 1);
        actualResults = new int[]{testTable.getPlayers()[0].getWins(), testTable.getPlayers()[0].getLosses(), testTable.getPlayers()[0].getPushes()};
        System.out.println(testTable.getPlayers()[0].toString());
        assertArrayEquals(expectedResults, actualResults);
    }
}
