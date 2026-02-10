package com.amaris.blackjack_simulation_project;


import com.amaris.blackjack_simulation_project.model.Player;
import com.amaris.blackjack_simulation_project.model.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//TODO Implement command line version of selecting options
public class SimulatorDriver {
    public static void driver(String[] args) throws Exception {
        String outputFile;

        //if the arguments are too much or too little show usage
        if (args.length == 0 || args.length > 3) {

            System.err.println("Usage: java SimulatorDriver <number of players> <number of hands> [output_file]");
            throw new IllegalArgumentException("Usage: java SimulatorDriver <number of players> <number of hands> [output_file]]");

        }
        //Create a Default table
        Table defaultTable = new Table();

        try {

            //Set number of players to argument 0
            int numPlayers = Integer.parseInt(args[0]);
            //set number of hands to argument 1
            int numHands = Integer.parseInt(args[1]);
            if (args.length == 3) {//set the output destination to argument 2
                outputFile = args[2];
                //if argument 2 is null set it to the default
                if (outputFile == null) {
                    outputFile = "src/main/Results.txt";
                }
            } else
                outputFile = "src/main/Results.txt";
            // Create the path
            Path filePath = Paths.get(outputFile);
            Files.writeString(filePath, "Results:\n\n\n");
            //check if too many players at the table
            if (numPlayers > 6) {
                System.err.println("Number of players can't be more than 6");
                System.exit(1);
            }
            // if it's not then add the players to the table
            for (int i = 0; i < numPlayers; i++) {

                defaultTable.addPlayer(new Player());
                defaultTable.getPlayers()[i].setPlayerID(i + 1);
            }
            //try and load the deck
            defaultTable.loadDeck();
            //try and load the shoe
            defaultTable.loadShoe();
            //cut the shoe
            defaultTable.cutShoe(52);
            Files.writeString(filePath, "Initial shoe\n" + defaultTable.getShoe().toString() + "\n\n", StandardOpenOption.APPEND);

            System.out.println("Running " + numHands + " hands for " + numPlayers + " players");
            for (int i = 0; i < numHands; i++) {

                //deal initial cards
                defaultTable.dealInitialCards();
                //Have the players do their actions
                defaultTable.playerTurn();
                // have the dealer do their actions
                defaultTable.dealerTurn();
                //check the results of the game
                defaultTable.getDealer().checkTableState(defaultTable.getPlayers(), numPlayers);
                //Write the results to the output file

                Files.writeString(filePath, System.lineSeparator() + defaultTable.handResults(), StandardOpenOption.APPEND);
                //clean up the table
                defaultTable.getDealer().cleanTable(defaultTable.getPlayers(), numPlayers, defaultTable.getDiscard());
                if (defaultTable.getLastHand()) {

                    //add the rest of the shoe to the discard
                    defaultTable.getDiscard().addAll(defaultTable.getShoe().reversed());
                    //write the final state of discard to output file
                    Files.writeString(filePath, "Discard:" + defaultTable.getDiscard().toString() + "\n\n", StandardOpenOption.APPEND);
                    //empty the shoe
                    defaultTable.getShoe().clear();
                    //refill the shoe with the discard
                    defaultTable.getShoe().addAll(defaultTable.getDiscard());
                    //empty the discard
                    defaultTable.getDiscard().clear();
                    //Shuffle the shoe again
                    defaultTable.shuffleShoe();
                    //cut the shoe
                    defaultTable.cutShoe(52);
                    //reset last hand
                    defaultTable.setLastHand(false);


                }

            }
            //add the rest of the shoe to the discard
            defaultTable.getDiscard().addAll(defaultTable.getShoe().reversed());
            //write the final state of discard to output file
            try {
                Files.writeString(filePath, "\n\nFinal discard: \n" + defaultTable.getDiscard().toString(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {

            System.err.println(e.getMessage());
            System.err.println("Usage: java SimulatorDriver <number of players> <number of hands> [output_file]");
            throw e;

        }


    }


}

