/**
 * UserSystem
 * This class holds the hashmap for all stored users, contains main method for creating a user
 * along with creating a diet, validating input and removing/retrieving users from hashmap.
 * This Class also handles writing and reading from a .dat file to preserve stored users info
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

import java.io.*;
import java.util.*;

public class UserSystem implements Serializable {
    /**
     * Hashmap that stores a newly created user's information.
     * Usernames are stored as keys
     */
    private HashMap<String, User> users;
    /**
     * Height (in cm) of tallest recorded person.
     */
    private static final int TALLEST_PERSON = 251;
    /**
     * Age of oldest recorded person.
     */
    private static final int OLDEST_PERSON = 122;
    /**
     * Weight (in kg) of heaviest recorded person.
     */
    private static final int HEAVIEST_PERSON = 605;

    private static final long serialVersionUID = 1L;

    public UserSystem() {

        this.users = new HashMap<>();
    }

    /**
     * Takes all necessary parameters to create a user.
     * Validates parameters before creating user object
     * Stores user in hashmap if input is valid
     *
     * @param userName      User's unique username
     * @param person        Contains all personal user information
     * @param fitnessGoal   Integer pertaining to a diet plan option
     * @param activityLevel Exercise value that is multiplied to get user's TDEE
     * @return Newly created user
     */
    public User addUser(String userName, Person person, int fitnessGoal, double activityLevel) {

        if (isValid(userName, person, fitnessGoal, activityLevel)) {
            Diet diet = createDiet(person, activityLevel, fitnessGoal);
            System.out.println("Diet created with recommended 45% carb, 25% protein, 30% fat calorie plan");

            User user = new User(userName, person, diet);
            users.put(userName, user);

            return users.get(userName);
        }

        return null;
    }

    /**
     * Checks if a string exceeds its maximum character limit.
     *
     * @param checkString String to be checked against character limit
     * @param charLimit   Limit of characters that cannot be exceeded by string
     * @return Truncated string if string exceeds character limit, otherwise returns unchanged string
     */
    public String checkCharLimit(String checkString, int charLimit) {
        if (checkString == null) {
            return "";
        }

        if (checkString.length() > charLimit) {
            checkString = checkString.substring(0, charLimit);
            System.out.println("Truncated to " + checkString + " for exceeding " + charLimit + " character limit.");
            return checkString;
        } else {
            return checkString;
        }
    }

    /**
     * Validates whether or not username is already in the Hashmap.
     *
     * @param userName User's username to search
     * @return True if username is found, otherweise false
     */
    public boolean validateUsername(String userName) {

        return users.containsKey(userName);
    }

    private boolean isValid(String userName, Person person, int fitnessGoal, double activityLevel) throws InvalidInputException {
        StringBuilder errors = new StringBuilder();
        String newLine = System.lineSeparator();

        try {

            if ((Character.toLowerCase(person.getGender()) != 'm') && (Character.toLowerCase(person.getGender()) != 'f')) {
                errors.append("Not a valid gender" + newLine);
            }
            if ((person.getAge() < 0) || (person.getAge() > OLDEST_PERSON)) {
                errors.append("Not a valid age" + newLine);
            }
            // 30.48cm = 1 foot minimum
            if ((person.getHeight() < 30.48) || (person.getHeight() > TALLEST_PERSON)) {
                errors.append("Not a valid height" + newLine);
            }
            if (person.getWeight() < 0 || (person.getWeight() > HEAVIEST_PERSON)) {
                errors.append("Not a valid weight" + newLine);
            }
            if ((userName.length() > 10) || (person.getName().length() > 25)) {
                errors.append("Username/name have exceeded character limit" + newLine);
            }
            if ((activityLevel < 1.2) || (activityLevel > 1.725)) {
                errors.append("Not a valid activity level" + newLine);
            }
            if ((fitnessGoal != 1) && (fitnessGoal != 2)) {
                errors.append("Not a valid fitness goal" + newLine);
            }

            if (errors.length() != 0) {
                throw new InvalidInputException(errors.toString());
            }

        } catch (NullPointerException e) {
            throw new InvalidInputException("Inputs cannot be null");
        }

        return true;
    }

    private Diet createDiet(Person person, double activityLevel, int fitnessGoal) throws InvalidInputException {

        double tdee = calculateTdee(person.getGender(), person.getAge(), person.getHeight(), person.getWeight(), activityLevel);
        double bmi = calculateBmi(person.getHeight(), person.getWeight());

        switch (fitnessGoal) {
            case 1:
                System.out.println("Bulking diet plan created");
                return new BulkingDiet(tdee, bmi, MacroDiets.DEFAULT);
            case 2:
                System.out.println("Cutting diet plan created");
                return new CuttingDiet(tdee, bmi, MacroDiets.DEFAULT);
            default:
                throw new InvalidInputException("Not a valid fitness goal");
        }

    }

    private double calculateTdee(char gender, int age, double height, double weight, double activityLevel) {

        /* Mifflin-St-Jeor Formula to calculate BMR(Basal Metabolic Rate)
           value returns calories the body burns while at rest
         */
        double bmr = (10 * weight) + (6.25 * height) - (5 * age);

        return Character.toLowerCase(gender) == 'm' ? activityLevel * (bmr + 5)
                : activityLevel * (bmr - 161);
    }

    private double calculateBmi(double height, double weight) {
        double cmToMeters = height / 100;

        return (weight / Math.pow(cmToMeters, 2));
    }

    /**
     * Retrieves user from Hashmap if username is found.
     *
     * @param userName User's username to retrieve
     * @return Corresponding user if username is found, otherwise throws an exception
     * @throws InvalidInputException If username does not exist
     */
    public User retrieveUser(String userName) throws InvalidInputException {
        if (users.get(userName) != null) {
            return users.get(userName);
        } else {
            throw new InvalidInputException("Username does not exist");
        }
    }

    /**
     * Deletes user if username is found in Hashmap.
     *
     * @param userName User's username to be deleted
     * @return True if user is successfully deleted, otherwise throws an exception
     * @throws InvalidInputException If username does not exist
     */
    public boolean deleteUser(String userName) throws InvalidInputException {
        if (users.remove(userName) != null) {
            return true;
        } else {
            throw new InvalidInputException("Username does not exist");
        }
    }

    /**
     * Loads user data from a .dat file and inserts it into the local Hashmap.
     * First checks if file exists, ignoring the method if the file is not present
     */
    public void loadUsers() {

        File f = new File("Users.dat");

        if (f.exists() && !f.isDirectory()) {
            System.out.println("Loading Data....");

            try (ObjectInputStream loadUsers = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Users.dat")))) {
                boolean eof = false;
                while (!eof) {
                    try {
                        User user = (User) loadUsers.readObject();
                        users.put(user.getUserName(), user);
                    } catch (EOFException e) {
                        eof = true;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * Outputs user information to a .dat file to be stored until application is run again.
     */
    public void saveUsers() {
        try (ObjectOutputStream saveUsers = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Users.dat")))) {
            for (User user : users.values()) {
                saveUsers.writeObject(user);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
}




