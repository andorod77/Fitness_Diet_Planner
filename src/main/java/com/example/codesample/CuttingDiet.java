/**
 * CuttingDiet
 * This class holds all information and methods for a user who is on a
 * cut (reduction of calories to lose weight)
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

public class CuttingDiet extends Diet {
    /**
     * Daily calorie intake factoring in deficit of calories for cut.
     */
    private double reducedCalories;
    /**
     * Deficit of calories to be factored in to user's calorie intake.
     */
    private int deficitCalories;

    private static final long serialVersionUID = 1L;

    public CuttingDiet(double tdee, double bmi, MacroDiets diet) {
        super(tdee, bmi, false, diet);
        this.deficitCalories = (int) (tdee * .20);
        this.reducedCalories = tdee - this.deficitCalories;

        System.out.println("Cutting diet plan created. Calorie deficit set to 20% of TDEE");
    }

    /**
     * Prints out expected weight lost in weekly, monthly and yearly increments.
     */
    @Override
    public void expectedProgress() {
        double[] expectedWeekMonthYear = calculateWeightChange(deficitCalories);

        System.out.printf("Expected weight lost in a week: %.2f lbs %.2f kg %n"
                        + "Expected weight lost in a month: %.2f lbs %.2f kg %n"
                        + "Expected weight lost in a year: %.2f lbs % .2f kg  %n",
                expectedWeekMonthYear[0], Conversions.poundsToKilograms(expectedWeekMonthYear[0]),
                expectedWeekMonthYear[1], Conversions.poundsToKilograms(expectedWeekMonthYear[1]),
                expectedWeekMonthYear[2], Conversions.poundsToKilograms(expectedWeekMonthYear[2]));

    }

    /**
     * Changes a user's daily calorie intake based on what option is passed.
     *
     * @param option Integer denoting calorie intake option user chooses
     */
    @Override
    public void changeCalorieIntake(int option) {
        double percentage;
        switch (option) {
            case 1:
                percentage = .10;
                System.out.println("Calorie deficit set at 10%");
                break;
            case 2:
                percentage = .20;
                System.out.println("Calorie deficit set at 20%");
                break;
            case 3:
                percentage = .30;
                System.out.println("Calorie deficit set at 30%");
                break;
            case 4:
                percentage = .40;
                System.out.println("Calorie deficit set at 40%");
                break;
            default:
                System.out.println("Not a valid option. No Action");
                return;
        }
        this.deficitCalories = (int) (getTdee() * percentage);
        this.reducedCalories = getTdee() - this.deficitCalories;
    }

    /**
     * Display's menu to change calorie deficit.
     * * @return Menu as a string
     */
    @Override
    public String changeCalorieOffset() {
        String newLine = System.lineSeparator();
        return "Choose preferred calorie deficit percentage" + newLine
                + "(1) 10% (slow)" + newLine
                + "(2) 20% (recommended)" + newLine
                + "(3) 30% (aggressive)" + newLine
                + "(4) 40% (very aggressive) !Caution";
    }

    public double getDailyCalories() {

        return this.reducedCalories;
    }

}


