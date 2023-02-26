package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;

public class IncomeReportFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    void BillOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.BILL,pane);
    }

    @FXML
    void CustomerOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }

    @FXML
    void DashboardOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ADMIN_DASHBOARD,pane);
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
    void IncomeOnAction(ActionEvent event) {
    }

    @FXML
    void SupplierOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    @FXML
    void placeOrderOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PLACE_ORDER,pane);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'IncomeReportForm.fxml'.";

    }


}
