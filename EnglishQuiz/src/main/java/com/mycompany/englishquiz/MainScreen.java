/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.englishquiz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Bee
 */
public class MainScreen implements Initializable{

    @FXML
    private Button bt_main_practice;
    @FXML
    private Button bt_main_test;
    @FXML
    private Button bt_main_manage;
    
    
    public MainScreen() {
    }
    
    
    
    @FXML
    private Label lb_welcome;

    public void welcome_User(String username) {
        lb_welcome.setText("Welcome " + username + "!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }

}
