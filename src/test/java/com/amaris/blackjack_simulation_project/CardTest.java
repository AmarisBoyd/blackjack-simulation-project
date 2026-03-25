package com.amaris.blackjack_simulation_project;

import com.amaris.blackjack_simulation_project.model.Card;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    public void getAbbrevTest() {

        Card testCard = new Card("Hearts", "Ten", 10);
        System.out.println(testCard.getAbbrev());


    }
}
