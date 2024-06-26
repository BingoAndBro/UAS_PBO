/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author BingoAndBro
 */
import model.Database;
import model.User;

public class RegisterUserController {
    private Database database;

    public RegisterUserController() {
        this.database = Database.getInstance();
    }

    public boolean registerUser(String username, String email, String password) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        User user = new User(username, email, password);
        return database.registerUser(user);
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}


