/**
 * UserSystemInvalidInputTest
 * This class tests the addUser and other public methods that throw an InvalidInputException
 * when an invalid value is passed as one of the parameters
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserSystemInvalidInputTest implements ArgumentsProvider {

    private UserSystemTest test;
    private UserSystem userSystem;

    /**
     * Initializes UserSystem and a test UserSystem variable that holds all the variables needed
     * by the class.
     */
    @BeforeEach
    void setup() {
        userSystem = new UserSystem();
        test = new UserSystemTest();
    }


    /**
     * Iterates through a stream of Person objects, each with a different field being invalid.
     * and tests that an InvalidInputException is thrown for each
     * @param invalidPerson Person object with Invalid parameter
     */
    @ParameterizedTest
    @ArgumentsSource(UserSystemInvalidInputTest.class)
    @DisplayName("Invalid Person Test")
    void addUser_InvalidInputPersonTest(Person invalidPerson) {
        assertThrows(InvalidInputException.class,
                () -> userSystem.addUser(test.getUserName(), invalidPerson, test.getFitnessGoal(), test.getActivityLevel()));

    }

    /**
     * Tests against an invalid username input, one exceeding the character limit and one null input.
     */
    @Test
    @DisplayName("Invalid Username Test")
    void addUser_InvalidInputTestUsername() {
        assertThrows(InvalidInputException.class, () ->
                userSystem.addUser("very long username", test.getPerson(), test.getFitnessGoal(), test.getActivityLevel()));
        assertThrows(InvalidInputException.class, () ->
                userSystem.addUser(null, test.getPerson(), test.getFitnessGoal(), test.getActivityLevel()));

    }

    /**
     * Tests against an invalid fitness goal input, one input less than 1 and the other greater than 2.
     */
    @Test
    @DisplayName("Invalid Fitness Goal Test")
    void addUser_InvalidInputTestFitnessGoal() {

        assertThrows(InvalidInputException.class, () ->
                userSystem.addUser(test.getUserName(), test.getPerson(), -1, test.getActivityLevel()));
        assertThrows(InvalidInputException.class, () ->
                userSystem.addUser(test.getUserName(), test.getPerson(), 3, test.getActivityLevel()));

    }

    /**
     * Tests against an invalid activity level input, one input less than 1.2 and the other greater that 1.725.
     */
    @Test
    @DisplayName("Invalid Activity Level Test")
    void addUser_InvalidInputTestActivity() {
        assertThrows(InvalidInputException.class, () ->
                userSystem.addUser(test.getUserName(), test.getPerson(), test.getFitnessGoal(), 5));
        assertThrows(InvalidInputException.class, () ->
                userSystem.addUser(test.getUserName(), test.getPerson(), test.getFitnessGoal(), -1));

    }

    /**
     * Tests attempt to retrieve a non-existent username, throws an InvalidInputException.
     */
    @Test
    @DisplayName("Invalid Retrieve User Test")
    void retrieveUser() {
        assertThrows(InvalidInputException.class,
                () -> userSystem.retrieveUser("Not Username"));
    }

    /**
     * Tests attempt to delete a non-existent username, throws an InvalidInputException.
     */
    @Test
    @DisplayName("Invalid Delete User Test")
    void deleteUser() {
        assertThrows(InvalidInputException.class,
                () -> userSystem.deleteUser("Not Username"));
    }

    /**
     * Stream of arguments containing Person objects with different invalid fields.
     * @param extensionContext Encapsulates the context in which the current test or container is being executed
     * @return A stream of invalid person objects
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

        Person invalidPersonName = new Person(null, 'm', 180.34, 74.46, 23);
        Person invalidPersonGender = new Person("Jose", 'i', 180.34, 74.46, 23);
        Person invalidPersonHeight = new Person("Jose", 'm', 700, 74.46, 23);
        Person invalidPersonWeight = new Person("Jose", 'm', 180.34, -1, 23);
        Person invalidPersonAge = new Person("Jose", 'm', 180.34, 74.46, -1);

        return Stream.of(
                Arguments.of(invalidPersonName),
                Arguments.of(invalidPersonGender),
                Arguments.of(invalidPersonHeight),
                Arguments.of(invalidPersonWeight),
                Arguments.of(invalidPersonAge)
        );
    }
}