/**
 * MacroDiets
 * This enum holds the values of several different diets with differing Macronutrient ratios
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

public enum MacroDiets {
    /**
     * Paleo Diet consisting of low/moderate carb, moderate protein, high fat.
     */
    PALEO("Paleo", .30, .25, .50),
    /**
     * Atkins Diet consisting of low carbs, high protein, high fat.
     */
    ATKINS("Atkins", .10, .30, .60),
    /**
     * Default diet consisting of moderate ratios of carbs, protein, fat.
     */
    DEFAULT("Default", .45, .25, .30),
    /**
     * Keto diet consisting of ver low carbs, moderate/low protein, very high fat.
     */
    KETO("Keto", .05, .20, .75);

    /**
     * Diet name.
     */
    private String name;
    /**
     * Holds percentage of calories from carbohydrates user can eat.
     */
    private double carbs;
    /**
     * Holds percentage of calories from protein user can eat.
     */
    private double protein;
    /**
     * Holds percentage of calories from fat user can eat.
     */
    private double fat;

    private static final long serialVersionUID = 1L;

    MacroDiets(String name, double carbs, double protein, double fat) {
        this.name = name;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public double getCarbs() {

        return carbs;
    }

    public double getProtein() {

        return protein;
    }

    public double getFat() {

        return fat;
    }

    /**
     * Displays amount of grams that should be consumed depending on the Macronutrient ratio set by the diet.
     * @return Carb, Protein, and Fat gram intakes as a string
     */
    public String displayMacroPercentages() {
        return "Carbs " + ((int) (this.carbs * 100))
                + "% Protein " + ((int) (this.protein * 100))
                + "% Fat " + ((int) (this.fat * 100)) + "%";
    }
}
