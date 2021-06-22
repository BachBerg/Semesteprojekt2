package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {
    Sensor S1 = new Sensor();

    @Test
    void readData() {
        S1.readData();
        Assertions.assertTrue(S1.readData() == null);
    }

    @Test
    void filter() {
        double[] test_EKGdata = new double[1000];
        S1.filter(test_EKGdata);
        for (int i = 0; i < test_EKGdata.length; i++) {
            Assertions.assertNotEquals(null, test_EKGdata[i]);
        }
    }
}