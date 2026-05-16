package com.amaris.blackjack_simulation_project;

import java.util.Scanner;

public class CLIMainMenu {
    public void mainMenu() {
        String menu = """
                Please select one of the following options:
                1) Initial setup
                2) Run basic simulation
                3) Advanced Simulation
                4) Exit
                """;
        System.out.println(menu);
    }

    static public int getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

}
