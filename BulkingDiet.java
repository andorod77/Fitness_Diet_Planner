/**
 * BulkingDiet
 * This class holds all information and methods for a user who is on a
 * bulk (surplus of calories to gain weight)
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

public class BulkingDiet extends Diet {
    /**
     * Returns true if user is lean bulking.
     * Calorie surplus is less when lean bulking
     */
    private boolean leanBulking;
    /**
     * Daily calorie intake factoring in added surplus of calories for bulk.
     */
    private double increasedCalories;
    /**
     * Surplus to be added to a user's calorie intake.
     */
    private int surplusCalories;

    private static final long serialVersionUID = 1L;

    public BulkingDiet(double tdee, double bmi, MacroDiets diet) {
        super(tdee, bmi, true, diet);
        this.surplusCalories = 500;
        this.increasedCalories = tdee + surplusCalories;
        this.leanBulking = false;

        System.out.println("Bulking diet plan created. Calorie surplus set to 500");
    }

    /**
     * Prints out expected weight gained in weekly, monthly and yearly increments.
     */
    @Override
    public void expectedProgress() {
        double[] expectedWeekMonthYear = calculateWeightChange(surplusCalories);

        System.out.printf("Expected weight gained in a week: %.2f lbs %.2f kg %n"
                        + "Expected weight gained in a month: %.2f lbs %.2f kg %n"
                        + "Expected weight gained in a year: %.2f lbs % .2f kg  %n",
                expectedWeekMonthYear[0], Conversions.poundsToKilograms(expectedWeekMonthYear[0]),
                expectedWeekMonthYear[1], Conversions.poundsToKilograms(expectedWeekMonthYear[1]),
                expectedWeekMonthYear[2], Conversions.poundsToKilograms(expectedWeekMonthYear[2]));

        System.out.println("divide values by half to get approximate expected muscle gain");

    }

    /**
     * Changes a user's daily calorie intake to lean bulking/regular bulking.
     * depending on their current calorie surplus.
     * @param option Integer denoting calorie intake option user chooses
     */
    @Override
    public void changeCalorieIntake(int option) {
        switch (option) {
            case 1:
                if (!this.leanBulking) {
                    this.surplusCalories = 250;
                    System.out.println("Switched to a lean bulking plan. surplus is now " + surplusCalories + " calories");
                    this.leanBulking = true;
                } else {
                    this.surplusCalories = 500;
                    System.out.println("Switched to a regular bulking plan. Surplus is now " + surplusCalories + " calories");
                    this.leanBulking = false;
                }
                break;
            case 2:
                System.out.println("No Action Taken");
                break;
            default:
                System.out.println("Not a valid option. No Action");
        }
        this.increasedCalories = getTdee() + surplusCalories;
    }

    /**
     * Display's menu to change calorie surplus.
     * * @return Menu as a string
     */
    @Override
    public String changeCalorieOffset() {
        String newLine = System.lineSeparator();
        return "Choose option" + newLine
                + "(1) Switch to a surplus of " + isLeanBulking() + newLine
                + "(2) No Action";

    }

    private String isLeanBulking() {
        return (!this.leanBulking) ? "250 calories (Lean Bulk)" : "500 calories (Regular Bulk)";
    }

    @Override
    public double getDailyCalories() {
        return increasedCalories;
    }
}

