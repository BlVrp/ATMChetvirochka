package com.example.atmchetvirochka.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EditButtonsController{

    @FXML
    private Label inputField;
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button clearButton;

    public void initialize(){
        clearButton.setOnAction(e -> this.clearNumber());
        button0.setOnAction(e-> this.inputNumber('0'));
        button1.setOnAction(e-> this.inputNumber('1'));
        button2.setOnAction(e-> this.inputNumber('2'));
        button3.setOnAction(e-> this.inputNumber('3'));
        button4.setOnAction(e-> this.inputNumber('4'));
        button5.setOnAction(e-> this.inputNumber('5'));
        button6.setOnAction(e-> this.inputNumber('6'));
        button7.setOnAction(e-> this.inputNumber('7'));
        button8.setOnAction(e-> this.inputNumber('8'));
        button9.setOnAction(e-> this.inputNumber('9'));
    }

    void inputNumber(char num){
        inputField.setText(inputField.getText()+num);
    }

    void clearNumber(){
        if(!inputField.getText().isEmpty()){
            inputField.setText(inputField.getText().substring(0, inputField.getText().length()-1));
        }
    }

}
