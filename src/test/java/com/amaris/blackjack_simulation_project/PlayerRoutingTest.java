package com.amaris.blackjack_simulation_project;

import com.amaris.blackjack_simulation_project.model.Card;
import com.amaris.blackjack_simulation_project.model.Hand;
import com.amaris.blackjack_simulation_project.model.Player;
import com.amaris.blackjack_simulation_project.strategy.Strategy;
import com.amaris.blackjack_simulation_project.utils.BlackjackTestUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerRoutingTest {

    @Mock
    private Strategy mockStrategy;

    @ParameterizedTest(name = "Routing Test: {0}")
    @CsvSource({
            "Pair of 8s, '8,8',  10", // Hand: 8,8 | Dealer: 10
            "Soft 17,    '11,6',  5", // Hand: A,6 | Dealer: 5
            "Hard 10,    '6,4',   2"  // Hand: 6,4 | Dealer: 2
    })
    void testStrategyRouting(String testName, String cardString, int dealerVal) throws Exception {
        // 1. Setup Player
        Player player = new Player();
        player.setStrategy(mockStrategy);
        ArrayList<Card> shoe = BlackjackTestUtils.parseShoe(cardString);

        // 3. Deal until the shoe is empty
        while (!shoe.isEmpty()) {
            player.dealCard(shoe, 0);
        }
        //create a dealer card
        Card dealerCard = new Card(dealerVal);

        // 3. Execute
        player.strategy(dealerCard);

        // 4. Verify Routing based on hand type
        if (testName.contains("Pair")) {
            verify(mockStrategy).checkPairStrategy(eq(dealerCard), any(Hand.class));
        } else if (testName.contains("Soft")) {
            verify(mockStrategy).checkSoftStrategy(eq(dealerCard), any(Hand.class));
        } else {
            verify(mockStrategy).checkHardStrategy(eq(dealerCard), any(Hand.class));
        }
    }
}
