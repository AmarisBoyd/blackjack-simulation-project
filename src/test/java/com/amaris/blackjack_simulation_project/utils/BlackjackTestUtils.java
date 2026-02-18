package com.amaris.blackjack_simulation_project.utils;

import com.amaris.blackjack_simulation_project.model.BlackjackAction;
import com.amaris.blackjack_simulation_project.model.Card;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class BlackjackTestUtils {
    // For test that are checking to see if a specific BlackJackAction will be taken
    public static Stream<Arguments> parseActionTest(String testInputs) {
        return Arrays.stream(testInputs.split(";"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    String[] parts = line.split(":");
                    String testName = parts[0].trim();
                    ArrayList<Card> shoe = parseShoe(parts[1]);
                    BlackjackAction expected = BlackjackAction.valueOf(parts[2].trim());

                    return Arguments.of(testName, shoe, expected);
                });
    }

    //Given a string of cards return an arraylist to act as the test "shoe"
    public static ArrayList<Card> parseShoe(String cards) {
        ArrayList<Card> shoe = Arrays.stream(cards.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(Card::new)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        Collections.reverse(shoe);
        return shoe;
    }

    //For parsing test that rely on a score being what is expected. EX if a players hand score needs to be
    // A certain number after actions are taken
    public static Stream<Arguments> parseScoreTests(String testInputs) {
        return Arrays.stream(testInputs.split(";"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    String[] parts = line.split(":");
                    String testName = parts[0].trim();
                    ArrayList<Card> shoe = parseShoe(parts[1]);


                    Integer expectedScore = Integer.parseInt(parts[2].trim());

                    return Arguments.of(testName, shoe, expectedScore);
                });
    }

    // For test that are determined by the end results of the game
    public static Stream<Arguments> parseResultsTests(String testInputs) {
        return Arrays.stream(testInputs.split(";"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    String[] parts = line.split(":");
                    String testName = parts[0].trim();

                    // Parse both hands using your existing logic
                    ArrayList<Card> playerDeck = parseShoe(parts[1]);
                    ArrayList<Card> dealerDeck = parseShoe(parts[2]);

                    int wins = Integer.parseInt(parts[3].trim());
                    int losses = Integer.parseInt(parts[4].trim());
                    int pushes = Integer.parseInt(parts[5].trim());

                    return Arguments.of(testName, playerDeck, dealerDeck, wins, losses, pushes);
                });
    }
}

