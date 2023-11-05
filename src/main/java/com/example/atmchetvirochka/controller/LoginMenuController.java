package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import com.example.atmchetvirochka.ApplicationContext;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginMenuController {
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
        System.out.println("here2");
        editButtonsController.setExternalInputField(pin);
        System.out.println("finished initialization");
        enterButton.setOnAction(e-> logIn());

    }

    void logIn(){
        ResponseInfo<Boolean> response = ApplicationContext.getConnectorService().authorize(new LoginInfo(ApplicationContext.getCardNumber(), pin.getText()));
        System.out.println(response.data);
        if(response.data){
            incorrectPIN.setText("");
            try {
                Application.changeScene("/com/example/atmchetvirochka/screens/mainmenu-view.fxml");
            } catch (IOException e) {
                incorrectPIN.setText("error");
            }

        }
        incorrectPIN.setText("incorrect pin");
    }
}
