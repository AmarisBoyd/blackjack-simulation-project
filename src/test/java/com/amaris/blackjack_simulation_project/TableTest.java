package com.amaris.blackjack_simulation_project;

import com.amaris.blackjack_simulation_project.model.Card;
import com.amaris.blackjack_simulation_project.model.Player;
import com.amaris.blackjack_simulation_project.model.Table;
import com.amaris.blackjack_simulation_project.utils.BlackjackTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;


public class TableTest {
    Table testTable;





    @BeforeEach
    public void setUp() {
        testTable = new Table();
        // load the card data from the Cards.Json file
        try {
            testTable.loadDeck();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Error loading Deck");
            System.exit(1);
        }
        // Load the shoe up with copies of the deck
        testTable.loadShoe();


    }

    @Test
    void loadDeck_Test() {

        Assertions.assertNotNull(testTable.getDeck());

    }

    @Test
    void loadShoe_Test() {
        Assertions.assertNotNull(testTable.getShoe());
    }

    @Test
    void shuffleShoe_Test() {
        String shoePath = "src/main/resources/Shoe.txt";
        String shuffledPath = "src/main/resources/Shuffled.txt";
        Path shoeFile = Path.of(shoePath);
        Path shuffledFile = Path.of(shuffledPath);
        try {
            //Write current configuration of the shoe to a file for easier testing
            Files.writeString(shoeFile, testTable.toString());
            //copy the array for another way of testing
            ArrayList<Card> oldShoe = new ArrayList<>(testTable.getShoe());

            //Shuffle the shoe before we start
            testTable.shuffleShoe();
            // Write results to another file to compare to make sure shuffle worked
            Files.writeString(shuffledFile, testTable.toString());
            // create a player with default strategy


            //test assertion using deep equals
            Assertions.assertFalse(Objects.deepEquals(oldShoe, testTable.getShoe()));
            //test assertion using content matching on files
            long sameFile = Files.mismatch(shuffledFile, shoeFile);
            Assertions.assertNotEquals(-1, sameFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void cutShoe_Test() {
        //make a copy of the shoe to compare
        ArrayList<Card> oldShoe = new ArrayList<>(testTable.getShoe());
        //set cut position to one deck in;
        int cutPosition = testTable.getShoe().size() - 52;
        testTable.cutShoe(cutPosition);
        Assertions.assertFalse(Objects.deepEquals(oldShoe, testTable.getShoe()));
        Assertions.assertNotEquals(0, testTable.getCutPosition());

    }

    @Test
    void addPlayer_Test() {
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);
        Assertions.assertNotNull(testTable.getPlayers()[0]);


    }

    @Test
    void addTwo_Players_Test() {
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);
        Player playerTwo = new Player();
        testTable.addPlayer(playerTwo);
        Assertions.assertNotNull(testTable.getPlayers()[0]);
        Assertions.assertNotNull(testTable.getPlayers()[1]);

    }

    @Test
    void add_X_Players_Test() {
        Player[] players = new Player[5];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
            testTable.addPlayer(players[i]);
        }
        for (int j = 0; j < 5; j++) {
            Assertions.assertNotNull(testTable.getPlayers()[j]);
        }
    }

    @Test
    void dealInitialCards_One_Player_Test() {
        int cutPosition = testTable.getShoe().size() - 52;
        testTable.cutShoe(cutPosition);
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);

        testTable.dealInitialCards();

        Assertions.assertNotNull(testTable.getPlayers()[0].getHand());
        Assertions.assertNotNull(testTable.getDealer().getDealerHand());
    }

    @Test
    void dealInitialCards_Two_Players_Test() {
        int cutPosition = testTable.getShoe().size() - 52;
        testTable.cutShoe(cutPosition);
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);
        Player playerTwo = new Player();
        testTable.addPlayer(playerTwo);
        testTable.dealInitialCards();
        for (int i = 0; i < testTable.getPlayerCount(); i++) {
            Assertions.assertNotNull(testTable.getPlayers()[i].getHand());
        }
        Assertions.assertNotNull(testTable.getDealer().getDealerHand());

    }

    @Test
    void dealInitialCards_Six_Players_Test() {
        Player[] players = new Player[5];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
            testTable.addPlayer(players[i]);
        }

        testTable.dealInitialCards();
        for (int i = 0; i < testTable.getPlayerCount(); i++) {
            Assertions.assertNotNull(testTable.getPlayers()[i].getHand());

        }
        Assertions.assertNotNull(testTable.getDealer().getDealerHand());


    }

    @ParameterizedTest(name = "Routing Test: {0}")
    @MethodSource("playerActionValues")
    void one_Player_Player_Action_Test(String testName, ArrayList<Card> testShoe, int expectedValue) {
        Table onePlayerTestTable = new Table();

        // create the player
        Player playerOne = new Player();
        //add the player
        onePlayerTestTable.addPlayer(playerOne);
        onePlayerTestTable.setShoe(testShoe);
        onePlayerTestTable.dealInitialCards();
        Assertions.assertNotNull(onePlayerTestTable.getPlayers()[0].getHand());
        onePlayerTestTable.playerTurn();
        Assertions.assertEquals(expectedValue, onePlayerTestTable.getPlayers()[0].getHand()[0].getScore());


    }

    @ParameterizedTest
    @MethodSource("dealerActionValues")
    void one_Player_Dealer_ActionTest(String testName, ArrayList<Card> testShoe, int expectedDealerHandValue) {
        // create the player
        Player playerOne = new Player();
        //add the player
        testTable.addPlayer(playerOne);
        //set the shoe with the test shoe
        testTable.setShoe(testShoe);
        // deal initial cards
        testTable.dealInitialCards();
        //have the players take their actions
        testTable.playerTurn();
        //have the dealer take their actions
        testTable.dealerTurn();

        Assertions.assertEquals(expectedDealerHandValue, testTable.getDealer().getDealerHand().getScore());


    }

    private static Stream<Arguments> playerActionValues() {
        String testInputs = """
                Hard Value 18: 8,2,10,8:18;
                Soft Value 19: 5,10,11,10,3:19;
                Split Test 17: 8,10,8,10,9,10,10:17;
                Bust Test 0:   10,10,5,10,10:0
                """;
        return BlackjackTestUtils.parseScoreTests(testInputs);
    }


    private static Stream<Arguments> dealerActionValues() {

        String testInputs = """
                Player Stands Dealer wins:8,3,10,10,8:21;
                Player Stand Dealer Looses: 11,10,9,8,3,10:18;
                Player Bust Dealer Wins:10,10,9,7,8,10,5:17;
                Player Bust one Hand :10,10,3,2,10 : 12
                """;

        return BlackjackTestUtils.parseScoreTests(testInputs);
    }


}
