package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class TableTest {
    Table testTable;

    @BeforeEach
    public void setUp(){
        testTable = new Table();
        // load the card data from the Cards.Json file
        testTable.loadDeck();
        // Load the shoe up with copies of the deck
        testTable.loadShoe();


    }


    @Test
    void testLoadDeck() {

        Assertions.assertNotNull(testTable.deck);

    }

    @Test
    void testLoadShoe(){
        Assertions.assertNotNull(testTable.getShoe());
    }
    @Test
    void testShuffleShoe(){
        String shoePath ="src/main/resources/Shoe.txt";
        String shuffledPath ="src/main/resources/Shuffled.txt";
        Path shoeFile = Path.of(shoePath);
        Path shuffledFile = Path.of(shuffledPath);
        try {
            //Write current configuration of the shoe to a file for easier testing
            Files.writeString(shoeFile,testTable.toString());
            //copy the array for another way of testing
            ArrayList<Card> oldShoe = new ArrayList<>(testTable.getShoe());

            //Shuffle the shoe before we start
            testTable.shuffleShoe();
            // Write results to another file to compare to make sure shuffle worked
            Files.writeString(shuffledFile,testTable.toString());
            // create a player with default strategy


            //test assertion using deep equals
            Assertions.assertFalse(Objects.deepEquals(oldShoe, testTable.getShoe()));
            //test assertion using content matching on files
            long sameFile = Files.mismatch(shuffledFile,shoeFile);
            Assertions.assertNotEquals(-1, sameFile);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    @Test
    void testCutShoe(){
        //make a copy of the shoe to compare
        ArrayList<Card> oldShoe = new ArrayList<>(testTable.getShoe());
        //set cut position to one deck in;
        int cutPosition = testTable.shoe.size()-52;
        testTable.cutShoe(cutPosition);
        Assertions.assertFalse(Objects.deepEquals(oldShoe, testTable.getShoe()));
        Assertions.assertNotEquals(0, testTable.getCutPosition());

    }
    @Test
    void testAddPlayer(){
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);
        Assertions.assertNotNull(testTable.players[0]);





    }
    @Test
    void testAddTwoPlayers(){
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);
        Player playerTwo = new Player();
        testTable.addPlayer(playerTwo);
        Assertions.assertNotNull(testTable.players[0]);
        Assertions.assertNotNull(testTable.players[1]);

    }
    @Test
    void testAddXPlayers(){
        Player[] players= new Player[5];
        for(int i=0;i<players.length;i++){
            players[i] = new Player();
            testTable.addPlayer(players[i]);
        }
        for(int j=0;j<5;j++){
            Assertions.assertNotNull(testTable.players[j]);
        }
    }
    @Test
    void testDealInitialCardsOnePlayer(){
        int cutPosition = testTable.shoe.size()-52;
        testTable.cutShoe(cutPosition);
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);

        testTable.dealInitialCards();

        Assertions.assertNotNull(testTable.players[0].getHand());
        Assertions.assertNotNull(testTable.getDealer().getDealerHand());
    }
    @Test
    void testDealInitialCardsTwoPlayers(){
        int cutPosition = testTable.shoe.size()-52;
        testTable.cutShoe(cutPosition);
        Player playerOne = new Player();
        testTable.addPlayer(playerOne);
        Player playerTwo = new Player();
        testTable.addPlayer(playerTwo);
        testTable.dealInitialCards();
        for (int i = 0; i < testTable.getPlayerCount(); i++) {
           Assertions.assertNotNull(testTable.players[i].getHand());
        }
        Assertions.assertNotNull(testTable.getDealer().getDealerHand());

    }
    @Test
    void testDealInitialCardsSixPlayers(){
        Player[] players= new Player[5];
        for(int i=0;i<players.length;i++){
            players[i] = new Player();
            testTable.addPlayer(players[i]);
        }

        testTable.dealInitialCards();
        for (int i = 0; i < testTable.getPlayerCount(); i++) {
            Assertions.assertNotNull(testTable.players[i].getHand());

        }
        Assertions.assertNotNull(testTable.getDealer().getDealerHand());


    }

    private static Object[][] playerActionValues() {
        Object[][] actionValues = new Object[4][2];
        /*
         * player card
         * dealer card
         * player card
         * works backwards
         * */
        ArrayList<Card> hardValueTest = new ArrayList<>(List.of(
                new Card(8),
                new Card(10),
                new Card(2),
                new Card(10),
                new Card(8)
        ));
        ArrayList<Card> softValueTest = new ArrayList<>(List.of(
                new Card(10),
                new Card(3),
                new Card(10),
                new Card(5),
                new Card(10),
                new Card(11)
        ));
        ArrayList<Card> splitTest = new ArrayList<>(List.of(
                new Card(10),
                new Card(10),
                new Card(9),
                new Card(10),
                new Card(8),
                new Card(10),
                new Card(8)
        ));
        ArrayList<Card> bustTest = new ArrayList<>(List.of(
                new Card(10),
                new Card(10),
                new Card(3),
                new Card(10),
                new Card(10)
        ));
        actionValues[0] = new Object[]{hardValueTest, 18};
        actionValues[1] = new Object[]{softValueTest, 19};
        actionValues[2] = new Object[]{splitTest, 17};
        actionValues[3] = new Object[]{bustTest, 23};
        return actionValues;
    }

    @ParameterizedTest
    @MethodSource("playerActionValues")
    void onePlayerPlayerActionTest(ArrayList<Card> testShoe, int expectedValue) {
        // create the player
        Player playerOne = new Player();
        //add the player
        testTable.addPlayer(playerOne);
        testTable.setShoe(testShoe);
        testTable.dealInitialCards();
        Assertions.assertNotNull(testTable.getPlayers()[0].getHand());
        testTable.playerActions();
        Assertions.assertEquals(expectedValue, testTable.getPlayers()[0].getHandScore());


    }

    private static Object[][] dealerActionValues() {
        Object[][] actionValues = new Object[4][3];
        /*
         * player card
         * dealer card
         * player card
         * works backwards
         * */
        ArrayList<Card> playerStandsDealerWins = new ArrayList<>(List.of(
                new Card(8),
                new Card(3),
                new Card(10),
                new Card(10),
                new Card(8)
        ));
        ArrayList<Card> playerStandsDealerLoses = new ArrayList<>(List.of(
                new Card(10),
                new Card(3),
                new Card(8),
                new Card(9),
                new Card(10),
                new Card(11)
        ));
        ArrayList<Card> playerBustDealerWins = new ArrayList<>(List.of(
                new Card(10),
                new Card(10),
                new Card(9),
                new Card(7),
                new Card(8),
                new Card(10),
                new Card(5)
        ));
        ArrayList<Card> playerBustOneHand = new ArrayList<>(List.of(
                new Card(10),
                new Card(2),
                new Card(3),
                new Card(10),
                new Card(10)
        ));
        actionValues[0] = new Object[]{playerStandsDealerWins, 21};
        actionValues[1] = new Object[]{playerStandsDealerLoses, 18};
        actionValues[2] = new Object[]{playerBustDealerWins, 17};
        actionValues[3] = new Object[]{playerBustOneHand, 12};
        return actionValues;
    }

    @ParameterizedTest
    @MethodSource("dealerActionValues")
    void onePlayerDealerActionTest(ArrayList<Card> testShoe, int expectedDealerHandValue) {
        // create the player
        Player playerOne = new Player();
        //add the player
        testTable.addPlayer(playerOne);
        //set the shoe with the test shoe
        testTable.setShoe(testShoe);
        // deal initial cards
        testTable.dealInitialCards();
        //have the players take their actions
        testTable.playerActions();
        //have the dealer take their actions
        testTable.dealerActions();

        Assertions.assertEquals(expectedDealerHandValue, testTable.getDealer().getHandScore());


    }


    @Test
    void onePlayerTest() {
        // create the player
        Player playerOne = new Player();
        // add it to the table
        testTable.addPlayer(playerOne);
        //shuffle the card
        testTable.shuffleShoe();
        //cut the cards
        testTable.cutShoe(52);
        // deal the initial cards
        testTable.dealInitialCards();
        //do player action
        testTable.playerActions();
        //do dealer actions
        testTable.dealerActions();
        //get the results
        System.out.println(testTable.handResults());

    }
}
