/**
 * UserSystemTest
 * This class tests the main addUser method from the UserSystemTest class and all its components,
 * including the methods contained in the Diet objects. Also tests other public methods in UserSystem
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
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class UserSystemTest implements ArgumentsProvider {

    private UserSystem userSystem;
    private Person person;
    private String userName;
    private double activityLevel;
    private int fitnessGoal;
    private Diet diet;
    private User user;
    private User testAddUser;

    /**
     * Initializes all values to be used before each test method.
     */
    @BeforeEach
    void setup() {
      userSystem = new UserSystem();

      person = new Person("Jose", 'm', 180.34, 73.46, 23);
      userName = "Alejandro";
      activityLevel = 1.2;
      fitnessGoal = 2;
      diet = new CuttingDiet(2102.07, 22.59, MacroDiets.DEFAULT);
      user = new User(userName, person, diet);
      testAddUser = userSystem.addUser(userName, person, fitnessGoal, activityLevel);
    }

    /**
     * Compares various parameters belonging to the diet class between a
     * set expected value and the AddUser method.
     */
    @Test
    @DisplayName("Diet Parameters Test")
    void addUser_DietParametersTest() {
        assertAll("Diet Params",
                () -> assertEquals(user.getDiet().getDailyCalories(), testAddUser.getDiet().getDailyCalories(), .01),
                () -> assertEquals(user.getDiet().isBulking(), testAddUser.getDiet().isBulking()),
                () -> assertEquals(user.getDiet().getTdee(),testAddUser.getDiet().getTdee(), .01),
                () -> assertEquals(user.getDiet().displayBmi(), testAddUser.getDiet().displayBmi())
                );

    }


    /**
     * Tests against a calorie surplus/deficit input and returns expected weekly, monthly and
     * yearly weight loss/gain values in a double array
     */
    @Test
    @DisplayName("Diet Weight Change Test")
    void weightChange() {
        assertArrayEquals(new double[] {1.0,4.345,52.12}, user.getDiet().calculateWeightChange(500),.1);
    }

    /**
     * Iterates through a stream of user objects containing different weight catgerory values
     * for their BMI and tests whether the displayBmi method works properly
     * @param userInput User object
     * @param expected Expected BMI string to be returned
     */
    @ParameterizedTest
    @ArgumentsSource(UserSystemTest.class)
    @DisplayName("Display Bmi Test")
    void displayBmiTest(User userInput, String expected) {
        assertEquals(expected, userInput.getDiet().displayBmi());

    }


    /**
     * Tests calls to changing diet plan and changing calorie deficit/surplus.
     * to ensure user object correctly updates its data.
     */
    @Test
    @DisplayName("Diet Changing Calories Test")
    void ChangingCaloriesTest() {
        double changedCalories;

        user.changeDietPlan(); //changes to bulking diet plan
        changedCalories = user.getDiet().getDailyCalories() - 500;

        assertEquals(changedCalories, testAddUser.getDiet().getTdee(), .01);

        user.getDiet().changeCalorieIntake(1); // canges to lean bulking surplus
        changedCalories = user.getDiet().getDailyCalories() - 250;

        assertEquals(changedCalories, testAddUser.getDiet().getTdee(), .01);

        user.changeDietPlan(); //changes to cutting diet plan
        user.getDiet().changeCalorieIntake(3); // sets deficit to 30% of TDEE
        changedCalories = user.getDiet().getDailyCalories() + ((int) user.getDiet().getTdee() * .30);

        assertEquals(changedCalories, testAddUser.getDiet().getTdee(), .75);

        assertFalse(user.getDiet().isBulking());

    }

    /**
     * Tests method with valid inputs of strings not exceeding their character limit.
     * @param input Valid input string
     */
    @DisplayName("Should Not Exceed Char Limit")
    @ParameterizedTest
    @ValueSource(strings = {"tiny", "shortUser", "regular username", "very very long username"})
    void checkCharLimit_NotExceedingCharacters(String input) {
     int charLimit = 25;
     assertEquals(input, userSystem.checkCharLimit(input, charLimit));
    }

    /**
     * Tests method with invalid input of strings, all of whom exceed their character limits.
     * @param input Invalid input string
     */
    @DisplayName("Exceeds Char limit")
    @ParameterizedTest
    @ValueSource(strings = {"tiny", "shortUser", "regular username", "very very long username"})
    void checkCharLimit_ExceedsCharacters(String input) {
        int charLimit = 3;
        assertEquals(input.substring(0, charLimit), userSystem.checkCharLimit(input, charLimit));
    }

    /**
     * Tests method with a null and empty string input, both of which should return empty.
     * @param input null/empty username string
     */
    @DisplayName("Null and Empty String Input Test")
    @ParameterizedTest
    @NullAndEmptySource
    void checkCharLimit_EmptyAndNullStrings(String input) {
        int charLimit = 0;
        assertEquals("", userSystem.checkCharLimit(input, charLimit));
    }


    public Person getPerson() {
        return person;
    }

    public String getUserName() {
        return userName;
    }

    public double getActivityLevel() {
        return activityLevel;
    }

    public int getFitnessGoal() {
        return fitnessGoal;
    }

    /**
     * Stream of arguments containing user object strings displaying different BMI weight categories
     * @param extensionContext Encapsulates the context in which the current test or container is being executed
     * @return Stream of strings displaying BMI weight categories
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext){
        Person underweightPerson = new Person("underweight", 'f', 172.72, 54.43, 53);
        Person healthyPerson = new Person("healthy", 'm', 180.34, 74.83, 20);
        Person overweightPerson = new Person("overweight", 'm', 200.56, 104.29,35);
        Person obesePerson = new Person("obese", 'f', 150.32, 90, 33);

        User underweightBmi = new User("Jose1", underweightPerson, new CuttingDiet(1437.36, 18.24, MacroDiets.DEFAULT));
        User healthyBmi = new User("Jose2", healthyPerson, new BulkingDiet(2136.50, 23.00, MacroDiets.DEFAULT));
        User overweightBmi = new User("Jose2", overweightPerson, new CuttingDiet(2551.68, 25.92, MacroDiets.DEFAULT));
        User obeseBmi = new User("Jose2", obesePerson, new BulkingDiet(1816.2, 39.82, MacroDiets.DEFAULT));


        return Stream.of(
                Arguments.of(underweightBmi, "18.24(underweight)"),
                Arguments.of(healthyBmi, "23.00(healthy)"),
                Arguments.of(overweightBmi, "25.92(overweight)"),
                Arguments.of(obeseBmi, "39.82(obese)"));
    }
}