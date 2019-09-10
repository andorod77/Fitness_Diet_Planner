/**
 * Diet
 * This abstract class contains the base methods for displaying a user's diet information
 * and defines several abstract classes to be implemented by diet plan subclasses.
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

import java.io.Serializable;

public abstract class Diet implements Serializable {

    /**
     * Calories required to lose/gain 1 lbs of weight in a week.
     */
    private static final double CALORIES_REQUIRED = 3500;
    /**
     * Total Daily Energy Expenditure.
     * Amount of calories user should consume factoring in exercise
     */
    private double tdee;
    /**
     * Body Mass Index.
     * Screens weight categories for user to determine health
     */
    private double bmi;
    /**
     * Returns True if user is on a bulking diet plan.
     */
    private boolean isBulking;
    /**
     * Enum that holds the value for diet that alter users Macronutrient ratios.
     */
    private MacroDiets diets;

    private static final long serialVersionUID = 1L;

    public Diet(double tdee, double bmi, boolean isBulking, MacroDiets diets) {
        this.tdee = tdee;
        this.bmi = bmi;
        this.isBulking = isBulking;
        this.diets = diets;
    }

    public abstract void expectedProgress();

    public abstract double getDailyCalories();

    public abstract void changeCalorieIntake(int option);

    public abstract String changeCalorieOffset();


    public double getTdee() {

        return tdee;
    }

    public double getBmi() {

        return bmi;
    }

    public MacroDiets getDiets() {
        return diets;
    }

    /**
     * Displays a user's BMI(Body Mass Index) and weight category placement.
     *
     * @return Formatted BMI variable and weight category as a string
     */
    public String displayBmi() {
        String weightRange;

        if (this.bmi < 18.5) {
            weightRange = "(underweight)";
        } else if ((this.bmi >= 18.5) && (this.bmi <= 24.9)) {
            weightRange = "(healthy)";
        } else if ((this.bmi >= 25) && (this.bmi <= 29.9)) {
            weightRange = "(overweight)";
        } else {
            weightRange = "(obese)";
        }

        return String.format("%.2f", this.bmi) + weightRange;
    }

    /**
     * Displays all user diet plan details.
     */
    public void displayDietdetails() {
        int carbsInGrams = (int) (diets.getCarbs() * getDailyCalories()) / 4; //4 calories per gram of carbs
        int proteinInGrams = (int) (diets.getProtein() * getDailyCalories()) / 4; // 4 calories per gram of protein
        int fatInGrams = (int) (diets.getFat() * getDailyCalories()) / 9; // 9 calories per gram of fat

        System.out.println(diets.getName() + " Diet: " + diets.displayMacroPercentages());
        System.out.printf("TDEE(Total Daily Energy Expenditure): %.2f %n"
                + "Daily calorie intake: %.2f %n"
                + "BMI(Body Mass Index): %s %n"
                + "--------------- %n"
                + "Daily Macronutrient Intake %n"
                + "Carbs: %sg %n"
                + "Protein: %sg %n"
                + "Fat: %sg %n"
                + "--------------- %n", this.tdee, getDailyCalories(), displayBmi(), carbsInGrams, proteinInGrams, fatInGrams);
    }

    /**
     * Changes user's Macronutrient ratio based on what diet option the user chooses.
     *
     * @param dietOption integer value denoting user's diet choice
     */
    public void changeMacroDiet(int dietOption) {

        switch (dietOption) {
            case 1:
                System.out.println("Paleo diet active");
                this.diets = MacroDiets.PALEO;
                break;
            case 2:
                System.out.println("Keto diet active");
                this.diets = MacroDiets.KETO;
                break;
            case 3:
                System.out.println("Atkins diet active");
                this.diets = MacroDiets.ATKINS;
                break;
            case 4:
                System.out.println("Default diet active");
                this.diets = MacroDiets.DEFAULT;
                break;
            default:
                System.out.println("Not a valid option");
        }
    }

    /**
     * Calculate expected weight gained/lost weekly,monthly and yearly.
     *
     * @param calorieOffset Surplus/deficit of a user's daily calorie intake
     * @return Array of doubles containing expected values
     * @throws ArithmeticException If the calorieOffset variable is less than 0
     */
    public double[] calculateWeightChange(int calorieOffset) throws ArithmeticException {
        if (calorieOffset < 0) {
            throw new ArithmeticException("parameter cannot be less than 0");
        }

        double weeklyCalories = calorieOffset * 7;


        double expectedWeek = weeklyCalories / CALORIES_REQUIRED;
        double expectedMonth = (expectedWeek * 4.345);
        double expectedYear = (expectedMonth * 12);

        return new double[]{expectedWeek, expectedMonth, expectedYear};
    }

    public boolean isBulking() {

        return this.isBulking;
    }
}
