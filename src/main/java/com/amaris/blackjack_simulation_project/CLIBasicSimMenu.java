package com.amaris.blackjack_simulation_project;

import java.util.Scanner;

import static com.amaris.blackjack_simulation_project.CLIMainMenu.getIntInput;

public class CLIBasicSimMenu {
    static private final String outFile = "tempfile.txt";
    static private int hands = 0;
    static private int choice = 0;
    private static int threads;
    private static boolean multiThreaded = false;

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
                choice = getIntInput();
                choice = checkChoice();


            }
        } catch (Exception e) {
            System.out.println("Error");
        }


    }

    static public String formatBasicSimulationMenu() {

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
                hands = oneLineIntMenu("Choose the number of hands:");
                break;
            case 4:
                multiThreaded = oneLineBooleanMenu("Choose whether to run multiple threads: Yes/No:");
                threads = oneLineIntMenu("How many threads should run");
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid selection try again");
                choice = 0;
                break;
        }
        return choice;
    }

    static int oneLineIntMenu(String question) {
        int selection = 0;
        int confirm = 0;
        do {
            System.out.println(question);
            selection = getIntInput();


        } while (confirm != 0);
        return selection;

    }

    static boolean oneLineBooleanMenu(String question) {
        boolean selection = false;
        int confirm = 0;
        do {
            System.out.println(question);
            selection = getYesNoInput();
            confirm = confirmBool(selection);
        } while (confirm != 1);
        return selection;
    }


    private static boolean getYesNoInput() {
        Scanner input = new Scanner(System.in);
        boolean selection = false;

        String answer = input.nextLine();
        try {
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                selection = true;
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }


        return selection;
    }

    static int confirm(int selection) {
        int confirm = 0;
        do {
            System.out.printf("You have selected %d is this correct?" +
                    "1) Yes" +
                    "2) No ", selection);
            confirm = getIntInput();
            if (confirm > 1 || confirm < 0) {
                System.out.println("Invalid selection try again");
            }
        }
        while (confirm != 0 && confirm != 1);
        return confirm;
    }

    private static int confirmBool(boolean selection) {
        int confirm = 0;
        do {
            System.out.printf("You have selected %b is this correct?" +
                    "1) Yes" +
                    "2) No ", selection);
            confirm = getIntInput();
            if (confirm > 1 || confirm < 0) {
                System.out.println("Invalid selection try again");
            }
        }
        while (confirm != 0 && confirm != 1);
        return confirm;
    }
}

