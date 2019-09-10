/**
 * Main
 * This class handles most of the program's menu's and user input
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */

package com.example.codesample;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Main {
    private Main() {
        throw new AssertionError("Cannot Instantiate Main...");
    }

    /**
     * Scanner that handles all user input until the program terminates.
     */
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserSystem userSystem = new UserSystem();
        userSystem.loadUsers();

        int option = 0;
        boolean quit = false;
        User user = null;

        displayMainMenu();

        while ((option < 1) || (option > 3)) {

            try {
                System.out.println("Enter an option: ");
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.print("Not a suitable Input. ");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                scanner.close();
                throw new InputMismatchException("Cannot have null as an Input");
            }

            switch (option) {
                case 1:
                    user = createUser(userSystem);
                    break;
                case 2:
                    System.out.println("Enter Username: ");
                    String userName = scanner.nextLine();
                    user = userSystem.retrieveUser(userName);
                    break;
                case 3:
                    quit = true;
                    break;
                default:
                    System.out.println("Not a valid option");
            }
        }
        if (!quit) {
            userMenu(user, userSystem);
            userSystem.saveUsers();
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.printf("Fitness Plan Generator %n"
                + "---------------------- %n"
                + "(1) Create New User %n"
                + "(2) Load User %n"
                + "(3) Exit %n"
                + "---------------------- %n");
    }

    private static User createUser(UserSystem userSystem) {
        while (true) {
            try {
                System.out.println("Enter username (10 character limit): ");
                String userName = userSystem.checkCharLimit(scanner.nextLine(), 10);
                if (!userSystem.validateUsername(userName)) {

                    System.out.println("Enter name (25 character limit): ");
                    String name = userSystem.checkCharLimit(scanner.nextLine(), 25);
                    System.out.println("Gender? male/female: ");
                    char gender = scanner.next().charAt(0);
                    System.out.println("Enter age: ");
                    int age = scanner.nextInt();
                    double[] heightAndWeight = heightAndWeightMeasurement();
                    double height = heightAndWeight[0];
                    double weight = heightAndWeight[1];
                    double activityLevel = getActivityLevel();

                    System.out.printf("%nWhat is your fitness goal? %n"
                            + "(1) Build muscle %n"
                            + "(2) Lose fat %n");
                    System.out.println("Enter option: ");
                    int fitnessGoal = scanner.nextInt();


                    Person person = new Person(name, gender, height, weight, age);

                    return userSystem.addUser(userName, person, fitnessGoal, activityLevel);

                } else {
                    System.out.println("Username already taken. Try again....");
                }
            } catch (InputMismatchException e) {
                System.out.println("Not a suitable Input. Start Again... ");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                scanner.close();
                throw new InputMismatchException("Cannot have null as an Input");
            }

        }
    }

    private static double[] heightAndWeightMeasurement() {
        System.out.printf("Measurement System? %n"
                + "(1) Imperial(pounds, feet, inches) %n"
                + "(2) Metric(kilograms, centimeters) %n");

        int preference = 0;

        while ((preference < 1) || (preference > 2)) {
            System.out.println("Enter preference");
            preference = scanner.nextInt();

            double height;
            double weight;

            switch (preference) {
                case 1:
                    System.out.println("Enter height: ");
                    System.out.println("Feet?");
                    int feet = scanner.nextInt();
                    System.out.println("Inches?");
                    int inches = scanner.nextInt();
                    height = Conversions.feetAndInchesToCm(feet, inches);
                    System.out.println("Enter weight (lbs): ");
                    weight = Conversions.poundsToKilograms(scanner.nextDouble());
                    return new double[]{height, weight};
                case 2:
                    System.out.println("Enter height (in cm): ");
                    height = scanner.nextDouble();
                    System.out.println("Enter weight (kg): ");
                    weight = scanner.nextDouble();
                    return new double[]{height, weight};
                default:
                    System.out.println("Not a valid preference");
                    break;
            }
        }

        return null;
    }

    private static double getActivityLevel() {
        System.out.printf("Enter activity Level:%n"
                + "(1) not active %n"
                + "(2) slightly active(light exercise 1-3 days a week) %n"
                + "(3) active(moderate exercise 3-5 days a week) %n"
                + "(4) very active(strenuous exercise 6-7 days a week) %n");

        int option = 0;

        while ((option < 1) || (option > 4)) {
            System.out.print("Enter an Option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    return 1.2;
                case 2:
                    return 1.375;
                case 3:
                    return 1.55;
                case 4:
                    return 1.725;
                default:
                    System.out.println("Not a valid option");
                    break;
            }
        }
        return 0;
    }

    private static void userMenu(User user, UserSystem userSystem) {

        displayUserMenu();

        int option = 0;

        while (option != 8) {
            System.out.println("Enter User option: ");
            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        user.displayUserDetails();
                        break;
                    case 2:
                        user.changeDietPlan();
                        break;
                    case 3:
                        System.out.println(user.getDiet().changeCalorieOffset());
                        System.out.println("Enter Option:");

                        int calorieOption = scanner.nextInt();
                        user.getDiet().changeCalorieIntake(calorieOption);
                        break;
                    case 4:
                        user.getDiet().expectedProgress();
                        break;
                    case 5:
                        System.out.printf("Choose Diet %n"
                                        + "(1) Paleo Diet: %s %n"
                                        + "(2) Keto Diet: %s %n"
                                        + "(3) Atkins Diet: %s %n"
                                        + "(4) Default Diet: %s %n"
                                        + "Enter Option: %n", MacroDiets.PALEO.displayMacroPercentages(),
                                MacroDiets.KETO.displayMacroPercentages(), MacroDiets.ATKINS.displayMacroPercentages(),
                                MacroDiets.DEFAULT.displayMacroPercentages());
                        int dietOption = scanner.nextInt();

                        user.getDiet().changeMacroDiet(dietOption);
                        break;
                    case 6:
                        System.out.println("are you sure you want to delete this account? yes/no");
                        int delete = scanner.next().charAt(0);

                        if (Character.toLowerCase(delete) == 'y') {
                            userSystem.deleteUser(user.getUserName());
                            System.out.println("Account deleted. Exiting...");
                            return;
                        }

                    case 7:
                        displayUserMenu();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Not a valid option");
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Not a suitable Input. ");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                scanner.close();
                throw new InputMismatchException("Cannot have null as an Input");
            }
        }
    }

    private static void displayUserMenu() {
        System.out.printf("%nWelcome!  %n"
                + "---------------------- %n"
                + "(1) View details %n"
                + "(2) Change Diet Plan %n"
                + "(3) Change Calorie Intake %n"
                + "(4) See Expected Progress with Current Plan %n"
                + "(5) Change MacroNutrients %n"
                + "(6) Delete Account %n"
                + "(7) Display Menu Options %n"
                + "(8) Exit %n"
                + "---------------------- %n");
    }


}