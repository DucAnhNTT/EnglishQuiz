package com.mycompany.englishquiz.Code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DemoCodeNotGiaoDien {
    private static final String FILENAME = "C:\\Users\\Bee\\Desktop\\Git\\EnglishQuiz\\EnglishQuiz\\src\\main\\java\\SimpleDatabase\\user_data";

    public static void main(String[] args) {
        // write user data to file
        try {
            FileWriter writer = new FileWriter(FILENAME, true);
            writer.write("john,1234\n"); // username: john, password: 1234
            writer.write("jane,5678\n"); // username: jane, password: 5678
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        // read user data from file and authenticate user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File(FILENAME));
            boolean authenticated = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String fileUsername = parts[0];
                String filePassword = parts[1];
                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    System.out.println("Authentication successful!");
                    authenticated = true;
                    break;
                }
            }
            if (!authenticated) {
                System.out.println("Authentication failed!");
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}