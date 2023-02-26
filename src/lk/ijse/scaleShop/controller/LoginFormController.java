package lk.ijse.scaleShop.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;

import java.io.IOException;

public class LoginFormController {


    public AnchorPane pane;
    public TextField txtUsername;
    public PasswordField txtPassword;

    @FXML
    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        if(txtUsername.getText().equals("Admin")&& txtPassword.getText().equals("1234")) {
            Navigation.navigate(Routes.ADMIN_DASHBOARD,pane);
        }else if (txtUsername.getText().equals("Owner")&& txtPassword.getText().equals("5678")){
            Navigation.navigate(Routes.OWNER_DASHBOARD,pane);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);// line 1
            alert.setTitle("Incorrect Password");// line 2
            alert.setHeaderText("INVALID USER NAME OR PASSWORD!!!");// line 3
            alert.setContentText("Please, check your User Name and Password, and try again!");// line 4
            alert.showAndWait(); // line 5
        }
    }

    public void btnExitOnAction(ActionEvent event) {
        System.exit(0);
    }
}
