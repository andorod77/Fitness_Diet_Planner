/**
 * User
 * This class holds the user's username, personal detail values in the person object and diet plan information
 * in the diet object.
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * Username that uniquely identifies every user in the program.
     */
    private String userName;
    /**
     * Holds a user's personal and body composition details.
     */
    private Person person;
    /**
     * User's current diet plan.
     * Also holds Macronutrient ratio diets
     */
    private Diet diet;

    private static final long serialVersionUID = 1L;

    public User(String userName, Person person, Diet diet) {
        this.userName = userName;
        this.person = person;
        this.diet = diet;
    }

    public String getUserName() {
        return userName;
    }

    public Diet getDiet() {

        return diet;
    }


    /**
     * Displays both user's personal statistics and diet plan information.
     */
    public void displayUserDetails() {
        this.person.displayPersonDetails();
        this.diet.displayDietdetails();
    }

    /**
     * Switches to bulking/cutting diet plan depending on user's current plan.
     */
    public void changeDietPlan() {
        double tdee = this.diet.getTdee();
        double bmi = this.diet.getBmi();

        if (this.diet.isBulking()) {
            System.out.println("Switching to a Cutting diet plan");
            setDiet(new CuttingDiet(tdee, bmi, getDiet().getDiets()));
        } else {
            System.out.println("Switching to a Bulking diet plan");
            setDiet(new BulkingDiet(tdee, bmi, getDiet().getDiets()));
        }
    }

    private void setDiet(Diet diet) {
        this.diet = diet;
    }


}
