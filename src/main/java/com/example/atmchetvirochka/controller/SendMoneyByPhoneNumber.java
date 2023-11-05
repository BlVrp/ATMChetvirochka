package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import com.example.atmchetvirochka.ApplicationContext;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javax.lang.model.type.NullType;
import java.io.IOException;

public class SendMoneyByPhoneNumber {
    private boolean phone = false;
    @FXML
    private Label textForUser;
    @FXML
    private Label phoneNumber;
    @FXML
    private Button backButton;
    @FXML
    private Button enterButton;
    @FXML
    private Button downButton;
    @FXML
    private Button upButton;
    @FXML
    private Label incorrectCashNumber;
    @FXML
    private Label incorrectPhoneNumber;
    @FXML
    private Label cashNumber;
    @FXML
    private EditButtonsController editButtonsController;

    public void initialize(){
        editButtonsController.setExternalInputField(phoneNumber);
        enterButton.setOnAction(e -> changeField());
    }

    @FXML
    private void backToMainMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/loginmenu-view.fxml");
    }

    private void changeField(){
        phone = !phone;
        editButtonsController.setExternalInputField(phone?phoneNumber:cashNumber);
    }

    @FXML
    private void sendMoney(){
        ResponseInfo<NullType> response = ApplicationContext.getConnectorService().sendMoneyByPhoneNumber(new LoginInfo(ApplicationContext.getCardNumber(), ApplicationContext.getPin()),
                new TransactionInputInfo(null, Integer.parseInt(cashNumber.getText()), ""));
        if(response.success){

            try {
                Application.changeScene("/com/example/atmchetvirochka/screens/mainmenu-view.fxml");
            } catch (IOException e) {

            }

        }
        //    incorrectPIN.setText("incorrect pin");
    }
}
