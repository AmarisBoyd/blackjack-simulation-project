package com.amaris.blackjack_simulation_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


@SpringBootApplication
public class BlackjackSimulationProjectApplication {

    static void main(String[] args) {
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
            SimulatorDriver.driver(arguments);
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
