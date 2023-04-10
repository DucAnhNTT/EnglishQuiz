package com.mycompany.englishquiz.Code;


import java.text.SimpleDateFormat;
import java.util.Scanner;



public class Utils {
    public static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    public static final String DATABASE_URL = "jdbc:sqlite:src\\main\\resources\\Database\\Users.db";

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }
}