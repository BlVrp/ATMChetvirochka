package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class UserInfoMenuController {
    @FXML
    private Label textForUser;
    @FXML
    private Label personalInfoLabel;
    @FXML
    private Label balanceInfoLabel;
    @FXML
    private Button backButton;

    @FXML
    private void backToMainMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/loginmenu-view.fxml");
    }
}
