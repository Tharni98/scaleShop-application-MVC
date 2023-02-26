package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;

public class BillFormController {


    public ResourceBundle resources;


    public URL location;

    public AnchorPane pane;


    @FXML
    void CustomerOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }

    @FXML
    void DashboardOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ADMIN_DASHBOARD,pane);

    }
    @FXML
    void IncomeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.INCOME_REPORT,pane);
    }

    @FXML
    void LogoutOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.LOGIN,pane);
    }

    @FXML
    void ProductOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PRODUCT,pane);

    }
    @FXML
    public void BillOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    public void SupplierOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    @FXML
    public void placeOrderOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PRODUCT,pane);
    }
}
