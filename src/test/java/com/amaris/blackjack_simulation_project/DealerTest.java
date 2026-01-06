package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DealerTest {


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
}
