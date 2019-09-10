/**
 * ConversionsTest
 * This class tests the Conversion interface for valid parameters when converting height/weight
 * between metric and imperial units.
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ConversionsTest {

    /**
     * Tests whether parameters of feet and inches are accurately converted to centimeters.
     */
    @Test
    @DisplayName("Feet and Inches to Cm Valid Input")
    void feetAndInchesToCm_ValidHeight() {
        assertEquals(177.8, Conversions.feetAndInchesToCm(5, 10), .1);
    }

    /**
     * Tests invalid parameters of feet and inches, expecting an InvalidInputException to be thrown.
     */
    @Test
    @DisplayName("Feet and Inches to Cm Invalid Input")
    void feetAndInchesToCm_InvalidHeight() {
        assertAll("Negative and Zero Inputs",
                () -> assertThrows(InvalidInputException.class,
                        () -> Conversions.feetAndInchesToCm(0, 13)),
                () -> assertThrows(InvalidInputException.class,
                        () -> Conversions.feetAndInchesToCm(1, -1)));
    }

    /**
     * Tests whether a weight parameter in pounds is accurately converted to kilograms.
     */
    @Test
    @DisplayName("Pounds to Kilograms Valid Input")
    void poundsToKilograms_ValidInput() {
        assertEquals(74.82, Conversions.poundsToKilograms(165), 0.1);
    }

    /**
     * Tests invalid zero and negative weight parameters, expecting an ArithmeticException to be thrown.
     */
    @Test
    @DisplayName("Pounds to Kilograms Invalid Input")
    void poundsToKilograms_InvalidInput() {
        assertAll("Negative and Zero Inputs",
                () -> assertThrows(ArithmeticException.class,
                        () -> Conversions.poundsToKilograms(0)),
                () -> assertThrows(ArithmeticException.class,
                        () -> Conversions.poundsToKilograms(-1)));
    }

    /**
     * Tests whether a weight parameter in kilograms is accurately converted to pounds.
     */
    @Test
    @DisplayName("Kilograms to Pounds Valid Input")
    void kilogramsToPounds_ValidInput() {
        assertEquals(165, Conversions.kilogramsToPounds(74.82), 0.1);
    }

    /**
     * Tests invalid negative weight parameter, expecting an ArithmeticException to be thrown.
     */
    @Test
    @DisplayName("Kilograms to Pounds Invalid Input")
    void kilogramsToPounds_InvalidInput() {
        assertThrows(ArithmeticException.class,
                () -> Conversions.kilogramsToPounds(-1));
    }

    /**
     * Tests whether a height parameter of inches is accurately converted to centimeters.
     */
    @Test
    @DisplayName("Cm to Inches Valid Input")
    void cmToinches_ValidInput() {
        assertEquals(70, Conversions.cmToinches(177.8), .1);

    }

    /**
     * Tests invalid negative height parameter, expecting an ArithmeticException to be thrown.
     */
    @Test
    @DisplayName("Cm to Inches Invalid Input")
    void cmToinches_InvalidInput() {
        assertThrows(ArithmeticException.class,
                () -> Conversions.cmToinches(-1));

    }
}