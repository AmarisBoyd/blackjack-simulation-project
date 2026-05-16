package com.amaris.blackjack_simulation_project;

import static com.amaris.blackjack_simulation_project.CLIMainMenu.getInput;

public class CLIBasicSimMenu {
    static private final String outFile = "tempfile.txt";
    static private final int hands = 0;
    static private int choice = 0;

    public static void basicSimulationMenu() {

        try {
            while (choice != 5) {
                System.out.println(formatBasicSimulationMenu());
                System.out.println("""
                        1) Confirm
                        2) Change output file
                        3) Change Number of hand
                        4) Change multi threading
                        5) Exit
                        """);
                choice = getInput();
                choice = checkChoice();


            }
        } catch (Exception e) {
            System.out.println("Error");
        }


    }

    static public String formatBasicSimulationMenu() {
        boolean multiThreaded = false;
        return String.format("This will run the simulation %d %s outputting the results to %s %s"
                , hands, (hands > 1) ? "times" : "time"
                , outFile, (multiThreaded) ? "with multiple threads" : "without multiple threads");
    }

    static int checkChoice() {
        switch (choice) {
            case 1:
                // Run the simulation
                choice = 5;
                break;
            case 2:
                //menu for changing output file
                break;
            case 3:
                //menu for changing number of hands
                break;
            case 4:
                //menu for changing multithread options
                break;
            case 5:
                break;
        }
        return choice;
    }
}
