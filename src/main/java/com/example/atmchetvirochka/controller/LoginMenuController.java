package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.ApplicationContext;
import com.example.atmchetvirochka.service.SimpleConnectorService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class LoginMenuController {
    SimpleConnectorService simpleConnnectorService;
    @FXML
    private Label textForUser;

    @FXML
    private EditButtonsController editButtonsController;

    @FXML
    private Button enterButton;

    @FXML
    private Label incorrectPIN;

    @FXML
    private Label pin;

    public void initialize(){
        simpleConnnectorService = ApplicationContext.getConnectorService();
        editButtonsController.setExternalInputField(pin);

    }

    void logIn(){

    }
}
