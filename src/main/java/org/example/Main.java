package org.example;

import org.example.dao.DatabaseConnection;
import org.example.ui.Menu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing EcoDeliverTrack Database...");
        DatabaseConnection.initializeDatabase();

        Menu menu = new Menu();
        menu.start();
    }
}