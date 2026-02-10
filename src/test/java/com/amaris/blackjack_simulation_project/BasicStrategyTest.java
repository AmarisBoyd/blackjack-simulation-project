package com.amaris.blackjack_simulation_project;

import com.amaris.blackjack_simulation_project.model.*;
import com.amaris.blackjack_simulation_project.utils.BlackjackTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BasicStrategyTest {
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
    private static Stream<Arguments> pairValues() {
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
        return BlackjackTestUtils.parseTestInputs(testInputs);
    }


    private static Stream<Arguments> hardValues() {
         /*String to hold the test inputs
         format Test name: Mock Shoe: Expected Return
         ***Note***
         The mock shoe is in reverse order for now so the first card is the first card to the player then it alternates
         */
        String testInputs =
                """
                        Eight on Two:  6,2,2,2:HIT;\
                        Eight on Three:6,3,2,10:HIT;\
                        Eight on Four: 6,4,2,10:HIT;\
                        Eight on Five: 6,5,2,10:HIT;\
                        Eight on Six:  6,6,2,10:HIT;\
                        Eight on Seven:6,7,2,10:HIT;\
                        Eight on Eight:6,8,2,10:HIT;\
                        Eight on Nine: 6,9,2,10:HIT;\
                        Eight on Ten:  6,10,2,10:HIT;\
                        
                        Nine on Two:3,2,6,10:HIT;
                        Ten on Two: 6,2,4,10:DOH;
                        Eleven on Two: 6,2,5,10:DOH;
                        Twelve on Two: 10,2,2,10:HIT;
                        Thirteen on Two:10,2,3,10:STA;
                        Fourteen on Two:10,2,4,10:STA;
                        Fifteen on Two: 10,2,5,10:STA;
                        Sixteen on Two: 10,2,6,10:STA;
                        Seventeen on two:10,2,7,10:STA;
                        Eighteen on two:10,2,18,10:STA;
                        Nineteen on two:10,2,9,10:STA;
                        
                        
                        Twenty on Two:10,2,10,10:STA;
                        Twenty on Three:11,3,11,10:STA;\
                        Twenty on Four: 11,4,11,10:STA;\
                        Twenty on Five: 11,5,11,10:STA;\
                        Twenty on Six:  11,6,11,10:STA;\
                        Twenty on Seven:11,7,11,10:STA;\
                        Twenty on Eight:11,8,11,10:STA;\
                        Twenty on Nine: 11,9,11,10:STA;\
                        Twenty on Ten:  11,10,11,10:STA;\
                        
                        
                        Eight on ace:2,11,6,8:HIT;
                        Nine on ace:3,11,6,8:HIT;
                        Ten on ace:6,11,4,8:HIT;
                        Eleven on ace:6,11,5,8:DOH;
                        Twelve on ace:10,11,2,8:HIT;\
                        Thirteen on ace:10,11,3,8:HIT;\
                        Fifteen on ace:10,11,5,8:HIT;\
                        Sixteen on ace:10,11,6,8:HIT;\
                        Seventeen on ace:10,11,7,8:STA;\
                        Eighteen on ace:10,11,8,8:STA;\
                        Nineteen on ace:10,11,9,8:STA;\
                        Twenty on ace: 10,11,10,8:STA
                        """;
        return BlackjackTestUtils.parseTestInputs(testInputs);

    }



    public static Stream<Arguments> softValues() {
           /*String to hold the test inputs
         format Test name: Mock Shoe: Expected Return
         ***Note***
         The mock shoe is in reverse order for now so the first card is the first card to the player then it alternates
         */
        String testInputs =
                """
                        Soft Thirteen on Two:  11,2,2,2:HIT;
                        Soft Fourteen on Two:  11,2,3,2:HIT;
                        Soft Fifteen on Two:   11,2,4,2:HIT;
                        Soft Sixteen on Two:   11,2,5,2:HIT;
                        Soft Seventeen on Two: 11,2,6,2:STA;
                        Soft Eighteen on Two:  11,2,7,2:DOS;
                        Soft Nineteen on Two:  11,2,8,2:STA;
                        Soft Twenty on Two:    11,2,9,2:STA""";


        return BlackjackTestUtils.parseTestInputs(testInputs);


    }

/*
Test soft strategy
*/

    @ParameterizedTest(name = "[{index}] {0} | {1} | Expected: {2}")
    @MethodSource("softValues")
    void soft_Strategy_Test(String testName, ArrayList<Card> mockShoe, BlackjackAction expectedAction) {
        Hand currentHand = testPlayer.getHand()[testPlayer.getCurrentHand()];
        testTable.setShoe(mockShoe);
        testTable.dealInitialCards();


        try {
            assertEquals(expectedAction, testPlayer.getStrategy().checkSoftStrategy(testTable.getDealer().getUpCard(), currentHand));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @ParameterizedTest(name = "[{index}] {0} | Expected: {2}")
    @MethodSource("hardValues")
    void hard_Strategy_Test(String testName, ArrayList<Card> mockShoe, BlackjackAction expectedAction) {

        Hand currentHand = testPlayer.getHand()[testPlayer.getCurrentHand()];
        testTable.setShoe(mockShoe);
        testTable.getRules().setDoubleAfterSplitAllowed(false);
        testTable.dealInitialCards();


        try {
            assertEquals(expectedAction, testPlayer.getStrategy().checkHardStrategy(testTable.getDealer().getUpCard(), currentHand));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @ParameterizedTest
    @MethodSource("pairValues")
    void checkPAirStrategy_Test(String testName, ArrayList<Card> mockShoe, BlackjackAction expectedAction) {
        Hand currentHand = testPlayer.getHand()[testPlayer.getCurrentHand()];
        testTable.setShoe(mockShoe);
        testTable.getRules().setDoubleAfterSplitAllowed(false);
        testTable.dealInitialCards();


        try {
            System.out.println("Running Pair Test: " + testName.trim());
            assertEquals(expectedAction, testPlayer.getStrategy().checkPairStrategy(testTable.getDealer().getUpCard(), currentHand));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }


}
