package org.example.ui;

import org.example.dao.UserDao;
import org.example.model.User;
import java.util.Scanner;
import java.io.Console;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final UserDao userDao = new UserDao();
    private User currentUser = null;

    public void start() {
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showDashboard();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== EcoDeliverTrack System ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> register();
            case "2" -> login();
            case "3" -> {
                System.out.println("Exiting system. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    private void register() {
        System.out.print("Enter new username: ");
        String user = scanner.nextLine();


        String pass = getPasswordInput("Enter your own password: ");

        System.out.print("Enter role (DISPATCHER, WAREHOUSE_MANAGER, BILLING_STAFF, ADMIN): ");
        String role = scanner.nextLine().toUpperCase().replace(" ", "_");

        if (userDao.registerUser(user, pass, role)) {
            System.out.println("Registration successful! Status is PENDING. Await admin approval.");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String user = scanner.nextLine();


        String pass = getPasswordInput("Enter your password: ");

        currentUser = userDao.loginUser(user, pass);

        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.username());
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void showDashboard() {
        System.out.println("\n=== Dashboard ===");
        System.out.println("Logged in as: " + currentUser.username() + " (" + currentUser.role() + ")");
        System.out.println("Status: " + currentUser.status());
        System.out.println("1. Logout");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            currentUser = null;
            System.out.println("Logged out successfully.");
        }
    }


    private String getPasswordInput(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }
}