/**
 * Conversions
 * This interface handles all metric and imperial conversions necessary to the program
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

public interface Conversions {

    static double feetAndInchesToCm(int feet, int inches) {
        if ((feet <= 0) || ((inches >= 12) || (inches < 0))) {
            throw new InvalidInputException("Not a valid feet/inches input");
        }

        inches += (feet * 12);

        return (inches * 2.54);
    }

    static double poundsToKilograms(double weight) {
        if (weight <= 0) {
            throw new ArithmeticException("Input must be greater than 0");
        }
        return (weight / 2.205);
    }

    static double kilogramsToPounds(double weight) throws ArithmeticException {
        if (weight < 0) {
            throw new ArithmeticException("Negative numbers are not allowed");
        }
        return (weight * 2.205);
    }

    static int cmToinches(double height) {
        if (height <= 0) {
            throw new ArithmeticException("Input must be greater than 0");
        }
        return (int) (height / 2.54);
    }
}
