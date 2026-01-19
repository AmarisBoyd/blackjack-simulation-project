package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DealerTest {
    Table testTable;


    @BeforeEach
    public void setup() {
        testTable = new Table();

        Player testPlayer = new Player();
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

    public static Object[] dealerActionValues() {
        Object[] dealerActionValues = new Object[4];
        return dealerActionValues;
    }

    @ParameterizedTest
    @MethodSource("dealerActionValues")
    void Test_Check_State(Hand playerHand, Hand dealerHand, int expectedWins, int expectedLosses) {
        // get the players hand for readability
        Hand testHand = testTable.getPlayers()[0].getHand()[0];
        //add cards to the hand with a low value
        testHand.addCard(new Card(10));
        testHand.addCard(new Card(2));
        // add cards to dealer
        testTable.getDealer().getDealerHand().addCard(new Card(10));
        testTable.getDealer().getDealerHand().addCard(new Card(9));
        //check the table state
        testTable.getDealer().getDealerHand().updateScore();
        testTable.getDealer().checkTableState(testTable.getPlayers(), 1);
        System.out.println(testTable.handResults());
        assertEquals(1, testTable.getPlayers()[0].getLosses());


    }
}
