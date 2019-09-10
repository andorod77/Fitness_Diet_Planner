/**
 * Person
 * This class holds all personal details relating to the user
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

import java.io.Serializable;

public class Person implements Serializable {
    /**
     * Person's name (must be 25 characters max in order to work with program).
     */
    private String name;
    /**
     * Person's gender ( 'm'/'f' to represent male and female).
     */
    private char gender;
    /**
     * Person's weight (in kg).
     */
    private double height;
    /**
     * Person's height (in cm).
     */
    private double weight;
    /**
     * Person's age.
     */
    private int age;

    private static final long serialVersionUID = 1L;

    public Person(String name, char gender, double height, double weight, int age) {
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    /**
     * Displays user's personal information.
     */
    public void displayPersonDetails() {
        double weightInLbs = Conversions.kilogramsToPounds(this.weight);
        int feet = Conversions.cmToinches(this.height) / 12;
        int inches = Conversions.cmToinches(this.height) - (feet * 12);

        System.out.printf("name: %s %n"
                        + "age: %d %n"
                        + "weight: %.2f lbs / %.2f kg %n"
                        + "height: %d'%d / %.2f cm %n"
                        + "Gender: %c %n", this.name, this.age, weightInLbs, this.weight, feet, inches, this.height,
                this.gender);
    }
}

