package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import com.example.atmchetvirochka.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DefaultMenuController {
    @FXML
    private TextField cardNumber;
    @FXML
    private Label putCardLabel;
    @FXML
    private void goToLoginMenu(MouseEvent e) throws IOException {
        ApplicationContext.setCardNumber(cardNumber.getText());
        Application.changeScene("/com/example/atmchetvirochka/screens/loginmenu-view.fxml");
    }
}