package com.amaris.blackjack_simulation_project.utils;

import com.amaris.blackjack_simulation_project.model.BlackjackAction;
import com.amaris.blackjack_simulation_project.model.Card;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class BlackjackTestUtils {
    public static Stream<Arguments> parseTestInputs(String testInputs) {
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

    private static ArrayList<Card> parseShoe(String cards) {
        ArrayList<Card> shoe = Arrays.stream(cards.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(Card::new)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        Collections.reverse(shoe);
        return shoe;
    }
}

