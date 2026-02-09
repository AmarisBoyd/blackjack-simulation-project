package com.amaris.blackjack_simulation_project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SimulatorDriverTest {


    @BeforeEach
    public void setUp() {


    }


    @Test

    @Disabled
    void one_Player_Stress_Test() throws Exception {
        String[] driverArgs = {"1", "8000", "src/main/Test_Results.txt"};
        try {
            SimulatorDriver.driver(driverArgs);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Disabled
    @Test
    void driverTestThrowsIllegalArgumentException() throws Exception {
        String[] driverArgs = {"1", "300", "src/main/Test_Results.txt", "2"};
        try {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                SimulatorDriver.driver(driverArgs);
            });

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

