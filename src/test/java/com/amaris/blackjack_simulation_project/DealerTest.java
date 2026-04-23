package com.amaris.blackjack_simulation_project;

import com.amaris.blackjack_simulation_project.model.*;
import com.amaris.blackjack_simulation_project.utils.BlackjackTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class DealerTest {
    Table testTable;
    Player testPlayer;


    public static Stream<Arguments> checkStateValuesNoSplit() {
        // String to hold the test inputs
        // format [Test name]:[player hand cards]:[dealer hand cards]:[expected wins]:[expected losses]:[expected pushes]
        String data = """
                Player stays Dealer wins: 10,7 : 10,9  : 0 : 1 : 0;
                Player stays Player wins: 10,11: 10,10 : 1 : 0 : 0;
                Push:                     10,10: 10,10 : 0 : 0 : 1;
                Player bust:              10,5,8:10,10 : 0 : 1 : 0;
                Dealer bust:              10,7 : 10,5,8: 1 : 0 : 0
                """;
        return BlackjackTestUtils.parseResultsTests(data);
    }

    public static Object[][] checkStateSplitValues() {
        // String to hold the test inputs
        // format Test name: Mock Shoe: expected wins: expected losses: expected pushes
        String testInputs = """
                Player has win and a loss:11,9,8,8,10,8:1:1:0;\
                Both hands loose:11,9,10,8,10,8:0:2:0;\
                Both hands push :10,10,10,8,8,8:0:0:2;\
                Player bust both hands :10,4,10,4,10,8,10,8:0:2:0;\
                Player bust one hand Dealer wins:10,4,8,3,10,8,10,8:0:2:0;\
                Player bust one hand Dealer Loses:10,4,8,3,7,8,10,8:1:1:0;\
                Player bust one hand Dealer push:10,4,6,3,7,8,10,8:0:1:1;\
                """;
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
        // format Test name: Mock Shoe
        String testInputs =
                """
                         Player Bust :10,10,5,10,10;\
                         No Bust:10,10,10,10,10:[Joker,10];\
                        """
                ;
        ArrayList<String> expectedDiscards = new ArrayList<>();
        expectedDiscards.add("""
                The discard contains:
                 Joker: 10
                 Joker: 5
                 Joker: 10
                 Joker: 10
                 Joker: 10""");
        expectedDiscards.add("""
                The discard contains:
                 Joker: 10
                 Joker: 10
                 Joker: 10
                 Joker: 10""");

        ArrayList<Card> mockShoe = new ArrayList<>();
        String testName;
        //create an array of strings with the first parse of the test inputs
        String[] individualLine = testInputs.split(";");
        // set the number of rows in the object array equal to the size of the first parse array
        Object[][] dealerActionValues = new Object[individualLine.length][4];
        for (int k = 0; k < individualLine.length; k++) {
            //Parse each string in the new array a second time to get individual values
            String[] lineSplitToArray = individualLine[k].split(":");

            testName = lineSplitToArray[0];
            String[] listOfValuesToMakeCards = lineSplitToArray[1].split(",");
            for (String thirdParseInput : listOfValuesToMakeCards) {
                mockShoe.add(new Card(Integer.parseInt(thirdParseInput)));
            }
            String expectedDiscard = expectedDiscards.get(k);


            //Add the now parsed values to a new object and store it in row "k" of the object array
            dealerActionValues[k] = new Object[]{testName, mockShoe.clone(), expectedDiscard};
            mockShoe.clear();


        }
        return dealerActionValues;
    }

    public static Stream<Arguments> softValues() {
        String inputs = """
                11,8,8,8:true;
                11,6,7:false
                """;

        return Arrays.stream(inputs.split(";")).
                map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    String[] lineSplitToArray = line.split(":");
                    boolean expectedResults = Boolean.parseBoolean(lineSplitToArray[1]);
                    List<Card> cards = Arrays.stream(lineSplitToArray[0].split(","))
                            .map(String::trim)
                            .map(val -> new Card(Integer.parseInt(val)))
                            .toList();
                    return Arguments.of(cards, expectedResults);
                });

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
        //deal the card giving a fake index to be updated but never used
        testDealer.dealCard(mockShoe, 0);
        assertEquals(10, testDealer.getDealerHand().getScore());

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
    void checkBust_OneAce_Bust_Test() {
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
    @MethodSource("softValues")
    void checkBustSoftHands(List<Card> cardsAsList, boolean expectedResult) {
        ArrayList<Card> testCards = new ArrayList<>(cardsAsList);
        Hand curHand = testPlayer.getHand()[testPlayer.getCurrentHand()];
        System.out.println(testCards);
        Dealer dealer = new Dealer();
        while (!testCards.isEmpty()) {
            testPlayer.dealCard(testCards, 0);
        }
        System.out.println(curHand);
        Assertions.assertEquals(expectedResult, dealer.checkBustSoftAce(curHand));
        System.out.println(curHand);

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("checkStateValuesNoSplit")
    void checkState_No_Split_Test(String testName, ArrayList<Card> playerHand, ArrayList<Card> dealerHand,
                                  int expectedWins, int expectedLosses, int expectedPushes) {

        int[] expectedResults = {expectedWins, expectedLosses, expectedPushes};
        int[] actualResults;
        Player tempPlayer = this.testTable.getPlayers()[0];
        Dealer tempDealer = this.testTable.getDealer();
        while (!playerHand.isEmpty()) {
            tempPlayer.dealCard(playerHand, 0);
        }
        while (!dealerHand.isEmpty()) {
            tempDealer.dealCard(dealerHand, 0);
        }
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
        assertAll("Blackjack Results Logic",
                () -> assertEquals(expectedWins, tempPlayer.getWins(), "Wins count mismatch"),
                () -> assertEquals(expectedLosses, tempPlayer.getLosses(), "Losses count mismatch"),
                () -> assertEquals(expectedPushes, tempPlayer.getPushes(), "Pushes count mismatch")
        );


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
    void cleanTable_Hands_Test(String testName, ArrayList<Card> mockShoe, String expectedDiscard) {
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

        assertEquals(expectedDiscard.trim(), testDiscard.trim());

    }


}
