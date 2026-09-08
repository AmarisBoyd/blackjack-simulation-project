package com.amaris.blackjack_simulation_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


@SpringBootApplication
public class BlackjackSimulationProjectApplication {

    static void main(String[] args) {
        /*TODO
          1) Create design documents for the project so it all doesn't live in my notoriously unreliable brain
                a) reduce the ever bloating scope of the project which caused me to stop updating
          2) Add javadoc comments and documentation on how to run the program
          3) Refactor how aces and "soft" hands are  handled there should be an easier way that doesn't require the many checks or changing the value of cards
          4) Move cards from objects to enums once they are static values to reduce space
          5) Look into api design principles and implement them
          6) Finish CLI version of generating the data to be inserted into the database so that something like postman or another project can call on the api
          7) finish other TODO's
          8) Put this list in actual importance order at some point
          **/
        String[] arguments;
        if (args.length == 0) {
            arguments = new String[3];
            arguments[0] = String.valueOf(1);
            arguments[1] = String.valueOf(30);
        } else {
            arguments = args;
        }
        SpringApplication.run(BlackjackSimulationProjectApplication.class, args);

        try {

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Finished Simulation");
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Press any key to Exit");
        keyboard.nextLine();
        keyboard.close();
        System.exit(0);


    }


}
