package com.example.atmchetvirochka.controller;

import com.example.atmchetvirochka.Application;
import com.example.atmchetvirochka.ApplicationContext;
import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.service.ConnectorService;
import com.example.atmchetvirochka.service.SimpleConnectorService;
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
    private void initialize(){
        LoginInfo loginInfo = new LoginInfo(ApplicationContext.getCardNumber(), ApplicationContext.getPin());
        ConnectorService connectorService = ApplicationContext.getConnectorService();
            ResponseInfo<AccountDTO> response = connectorService.getAccountInformation(loginInfo);
            ResponseInfo<Long> balanceResponse = connectorService.getBalance(loginInfo);
            if(response.success&&balanceResponse.success){
                AccountDTO account = response.data;
                personalInfoLabel.setText(account.owner_name+" "+ account.owner_surname);
                balanceInfoLabel.setText("БАЛАНС: "+balanceResponse.data.toString());
            }
            else{
                backToMainMenu();
            }

        backButton.setOnAction(e-> backToMainMenu());
    }


    private void backToMainMenu() {
        try {
            Application.changeScene("/com/example/atmchetvirochka/screens/loginmenu-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
