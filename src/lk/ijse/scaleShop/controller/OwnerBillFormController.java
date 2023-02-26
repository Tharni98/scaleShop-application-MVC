package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;

public class OwnerBillFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    void DashboardOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_DASHBOARD,pane);
    }

    @FXML
    void EmployeeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_EMPLOYEE,pane);
    }

    @FXML
    void IncomeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_INCOME,pane);
    }

    @FXML
    void LogoutOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_DASHBOARD,pane);
    }

    @FXML
    void ProductOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_PRODUCT,pane);
    }

    @FXML
    public void BillOnAction(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'OwnerBillForm.fxml'.";

    }


}
