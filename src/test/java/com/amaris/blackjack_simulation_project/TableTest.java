package com.amaris.blackjack_simulation_project;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;


import static org.apache.tomcat.util.http.fileupload.FileUtils.*;

@SpringBootTest
public class TableTest {
    Table testTable;

    public TableTest() {
        testTable = new Table();
    }


    @Test
    void testLoadDeck() {

        testTable.loadDeck();
        Assertions.assertNotNull(testTable.deck);

    }
    @Test
    void testInitalizeShoeSize(){
        testTable.initalizeShoeSize();
        Assertions.assertEquals(52*6,testTable.shoe.length);
    }
    @Test
    void testLoadShoe(){
        testTable.loadDeck();
        testTable.initalizeShoeSize();
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
            // Initialize how big the shoe is
            testTable.initalizeShoeSize();
            // Load the shoe up with copies of the deck
            testTable.loadShoe();
            //Write current configuration of the shoe to a file for easier testing
            Files.writeString(shoeFile,testTable.toString());
            //copy the array for another way of testing
            Card[] oldShoe = Arrays.copyOf(testTable.getShoe(), testTable.getShoe().length);
            //Shuffle the shoe before we start
            testTable.shuffleShoe();
            // Write results to another file to compare to make sure shuffle worked
            Files.writeString(shuffledFile,testTable.toString());
            // create a player with default strategy


            //test assertion using deep equals
            Assertions.assertFalse(Objects.deepEquals(oldShoe, testTable.getShoe()));
            //test assertion using content matching on files
            long samefile = Files.mismatch(shuffledFile,shoeFile);
            Assertions.assertFalse(samefile==-1);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
