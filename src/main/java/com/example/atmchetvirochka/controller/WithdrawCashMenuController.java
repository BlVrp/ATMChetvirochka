package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import com.example.atmchetvirochka.ApplicationContext;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class WithdrawCashMenuController {
    @FXML
    private Label textForUser;
    @FXML
    private Label availableCashLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button enterButton;
    @FXML
    private Label incorrectNumber;
    @FXML
    private Label cashNumber;

    @FXML
    private EditButtonsController editButtonsController;
    @FXML
    private void initialize(){
        backButton.setOnAction(e->backToMainMenu());
        enterButton.setOnAction(e-> withdrawCash());
        editButtonsController.setExternalInputField(cashNumber);
    }
    private void backToMainMenu(){
        try {
            Application.changeScene("/com/example/atmchetvirochka/screens/mainmenu-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void withdrawCash(){
        incorrectNumber.setText("");
        LoginInfo loginInfo = new LoginInfo(ApplicationContext.getCardNumber(), ApplicationContext.getPin());
        ResponseInfo response =
                ApplicationContext.getConnectorService()
                        .withdrawMoney(loginInfo, Integer.parseInt(cashNumber.getText()));
        if(response.success){
            backToMainMenu();
        }else{
            incorrectNumber.setText("недостатньо коштів");
        }

    }
}
