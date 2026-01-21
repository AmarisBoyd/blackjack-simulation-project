package com.amaris.blackjack_simulation_project;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BlackjackSimulationProjectApplication {

    static void main(String[] args) {
        String[] arguments;
        if (args.length == 0) {
            arguments = new String[2];
            arguments[0] = String.valueOf(1);
            arguments[1] = String.valueOf(30);
        } else {
            arguments = args;
        }
//        SpringApplication.run(BlackjackSimulationProjectApplication.class, args);

        try {
            SimulatorDriver.driver(arguments);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);


    }


}
