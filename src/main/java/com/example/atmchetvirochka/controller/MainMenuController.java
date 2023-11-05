package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Label textForUser;

    @FXML
    private Button userInfoButton;

    @FXML
    private Button withdrawCashButton;

    @FXML
    private Button putCashOnCardButton;

    @FXML
    private Button sendMoneyOnCardButton;

    @FXML
    private Button sendMoneyByPhoneNumberButton;

    @FXML
    private Button sendMoneyForCarityButton;

    @FXML
    private Button removeCardButton;

    @FXML
    private void goToUserInfoMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/userinfomenu-view.fxml");
    }
    @FXML
    private void goToWithdrawCashMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/withdrawcashmenu-view.fxml");
    }
    @FXML
    private void goToPutCashOnCardMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/putcashoncardmenu-view.fxml");
    }
    @FXML
    private void goToSendMoneyOnCardMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/sendmoneyoncardmenu-view.fxml");
    }
    @FXML
    private void goToSendMoneyByPhoneNumberMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/sendmoneybyphonenumbermenu-view.fxml");
    }
    @FXML
    private void goToSendMoneyForCharityMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/sendmoneyforcharitymenu-view.fxml");
    }
    @FXML
    private void goToDefaultMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/defaultmenu-view.fxml");
    }
}
