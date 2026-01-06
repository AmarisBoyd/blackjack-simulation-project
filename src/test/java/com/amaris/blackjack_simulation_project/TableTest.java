package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

@SpringBootTest
public class TableTest {
    Table testTable;

    TableTest(){
        testTable = new Table();
    }
    @BeforeEach
    public void setUp(){
        // load the card data from the Cards.Json file
        testTable.loadDeck();
        // Load the shoe up with copies of the deck
        testTable.loadShoe();
        int cutPosition = testTable.shoe.size()-52;
        testTable.cutShoe(cutPosition);

    }


    @Test
    void testLoadDeck() {

        testTable.loadDeck();
        Assertions.assertNotNull(testTable.deck);

    }

    @Test
    void testLoadShoe(){
        testTable.loadDeck();
        testTable.loadShoe();
        Assertions.assertNotNull(testTable.getShoe());
    }
    @Test
    void testShuffleShoe(){
        String shoePath ="src/main/resources/Shoe.txt";
        String shuffledPath ="src/main/resources/Shuffled.txt";
        Path shoeFile = Path.of(shoePath);
        Path shuffledFile = Path.of(shuffledPath);
        try {
            // load the card data from the Cards.Json file
            testTable.loadDeck();
            // Load the shoe up with copies of the deck
            testTable.loadShoe();
            //Write current configuration of the shoe to a file for easier testing
            Files.writeString(shoeFile,testTable.toString());
            //copy the array for another way of testing
            ArrayList<Card> oldShoe = testTable.getShoe();
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
        // load the card data from the Cards.Json file
        testTable.loadDeck();

        // Load the shoe up with copies of the deck
        testTable.loadShoe();
        //make a copy of the shoe to compare
        ArrayList<Card> oldShoe = testTable.getShoe();
        //set cut position to one deck in;
        int cutPosition = testTable.shoe.size()-52;
        testTable.cutShoe(cutPosition);
        Assertions.assertFalse(Objects.deepEquals(oldShoe, testTable.getShoe()));
        Assertions.assertNotEquals(0, testTable.getCutPosition());

    }
    @Test
    void testAddPlayer(){

        int cutPosition = testTable.shoe.size()-52;
        testTable.cutShoe(cutPosition);
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
        System.out.println(testTable.players[0]);
        Assertions.assertNotNull(testTable.players[0].getHand());
        Assertions.assertNotNull(testTable.dealer.getDealerHand());
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
        Assertions.assertNotNull(testTable.dealer.getDealerHand());

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
        Assertions.assertNotNull(testTable.dealer.getDealerHand());


    }
}
