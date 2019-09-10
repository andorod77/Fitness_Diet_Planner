/**
 * InvalidInputException
 * This class contains a custom exception that is thrown when invalid parameters are
 * used in an attempt to create a user using the Usersystem class
 *
 * @version 1.0
 * @author Jose Alejandro Rodriguez
 * Copyright, 2019,
 */
package com.example.codesample;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

}
