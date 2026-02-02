package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicStrategyTest {
    Table testTable;
    Player testPlayer;
    Dealer testDealer;


    @BeforeEach
    public void setup() {
        testTable = new Table();
        testPlayer = new Player();
        testTable.addPlayer(testPlayer);
        testDealer = new Dealer();
        testTable.addDealer(testDealer);
    }

    /*
  Test of pair strategy
   */
    private static Object[][] pairValues() {
        /*String to hold the test inputs
         format Test name: Mock Shoe: Expected Return
         ***Note***
         The mock shoe is in reverse order for now so the first card is the first card to the player then it alternates
         */
        String testInputs =
                """
                        Twos on Two:  2,2,2,2:SDH;\
                        Twos on Three:2,3,2,10:SDH;\
                        Twos on Four: 2,4,2,10:SPL;\
                        Twos on Five: 2,5,2,10:SPL;\
                        Twos on Six:  2,6,2,10:SPL;\
                        Twos on Seven:2,7,2,10:SPL;\
                        Twos on Eight:2,8,2,10:HIT;\
                        Twos on Nine: 2,9,2,10:HIT;\
                        Twos on Ten:  2,10,2,10:HIT;\
                        
                        Threes on Two:3,2,3,10:SDH;\
                        Fours on Two: 4,2,4,10:HIT;\
                        Fives on Two: 5,2,5,10:DOH;\
                        Six's on Two: 6,2,6,10:SDH;\
                        Sevens on Two:7,2,7,10:SPL;\
                        Eights on Two:8,2,8,10:SPL;\
                        Nines on Two: 9,2,9,10:SPL;\
                        Tens on Two:  10,2,10,10:STA;\
                        
                        Aces on Two:  11,2,11,10:SPL;\
                        Aces on Three:11,3,11,10:SPL;\
                        Aces on Four: 11,4,11,10:SPL;\
                        Aces on Five: 11,5,11,10:SPL;\
                        Aces on Six:  11,6,11,10:SPL;\
                        Aces on Seven:11,7,11,10:SPL;\
                        Aces on Eight:11,8,11,10:SPL;\
                        Aces on Nine: 11,9,11,10:SPL;\
                        Aces on Ten:  11,10,11,10:SPL;\
                        
                        Twos on ace:2,11,2,8:HIT;\
                        Threes on ace:3,11,3,8:HIT;\
                        Fours on ace:4,11,4,8:HIT;\
                        Fives on ace:5,11,5,8:HIT;\
                        Sixes on ace:6,11,6,8:HIT;\
                        Sevens on ace:7,11,7,8:HIT;\
                        Eights on ace:8,11,8,8:SPL;\
                        Nines on ace:9,11,9,8:STA;\
                        Tens on ace:10,11,10,8:STA;\
                        Aces on ace:11,11,11,8:SPL;\
                        Error:21,2,2,2:SPL""";
        ArrayList<Card> mockShoe = new ArrayList<>();
        String testName;
        //create an array of strings with the first parse of the test inputs
        String[] firstParseInputs = testInputs.split(";");

        // set the number of rows in the object array equal to the size of the first parse array
        Object[][] pairNoDoubleValues = new Object[firstParseInputs.length][3];
        for (int k = 0; k < firstParseInputs.length; k++) {
            //Parse each string in the new array a second time to get individual values
            String[] secondParseInputs = firstParseInputs[k].split(":");
            testName = secondParseInputs[0];
            BlackjackAction expectedResult = BlackjackAction.valueOf(secondParseInputs[2]);
            String[] thirdParseInputs = secondParseInputs[1].split(",");
            for (String thirdParseInput : thirdParseInputs) {
                mockShoe.add(new Card(Integer.parseInt(thirdParseInput.trim())));
            }
            //reverse the shoe so it works as expected
            mockShoe = new ArrayList<>(mockShoe.reversed());


            //Add the now parsed values to a new object and store it in row "k" of the object array
            pairNoDoubleValues[k] = new Object[]{testName, mockShoe.clone(), expectedResult};
            mockShoe.clear();
        }
        return pairNoDoubleValues;
    }

    @ParameterizedTest
    @MethodSource("pairValues")
    void checkPAirStrategy_Test(String testName, ArrayList<Card> mockShoe, BlackjackAction expectedAction) {
        Hand currentHand = testPlayer.getHand()[testPlayer.getCurrentHand()];
        testTable.setShoe(mockShoe);
        testTable.rules.setDoubleAfterSplitAllowed(false);
        testTable.dealInitialCards();


        try {
            System.out.println("Running Pair Test: " + testName.trim());
            assertEquals(expectedAction, testPlayer.strategy.checkPairStrategy(testTable.getDealer().getUpCard(), currentHand));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private static Object[][] hardValues() {
        return null;
    }

    @ParameterizedTest
    @MethodSource("hardValues")
    void hard_Strategy_Test() {


    }


    public static Object[][] softValues() {
        return null;
    }

	/*
	Test soft strategy
	 */

    @ParameterizedTest
    @MethodSource("softValues")
    void soft_Strategy_Test() {


    }

}
