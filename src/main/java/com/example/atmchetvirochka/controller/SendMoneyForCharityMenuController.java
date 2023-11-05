package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import com.example.atmchetvirochka.ApplicationContext;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.lang.model.type.NullType;
import java.io.IOException;

public class SendMoneyForCharityMenuController {
    String charityCard = "11111111";
    @FXML
    private Label textForUser;
    @FXML
    private Button backButton;
    @FXML
    private Button enterButton;
    @FXML
    private Label incorrectNumber;
    @FXML
    private Label cashNumber;
    @FXML
    private Label exception;
    @FXML
    private EditButtonsController editButtonsController;

    public void initialize(){
        editButtonsController.setExternalInputField(cashNumber);
    }

    @FXML
    private void backToMainMenu() throws IOException {
        Application.changeScene("/com/example/atmchetvirochka/screens/mainmenu-view.fxml");
    }

    @FXML
    private void sendMoney(){
        try {
            ResponseInfo<NullType> response = ApplicationContext.getConnectorService().sendMoneyByCardNumber(new LoginInfo(ApplicationContext.getCardNumber(), ApplicationContext.getPin()),
                    new TransactionInputInfo(charityCard, Integer.parseInt(cashNumber.getText()), null));
            if(response.success){
                exception.setText("Success!");
            }
            else exception.setText(response.message);
        }
        catch (Exception e){
            exception.setText("Incorrect amount");
        }
    }
}
