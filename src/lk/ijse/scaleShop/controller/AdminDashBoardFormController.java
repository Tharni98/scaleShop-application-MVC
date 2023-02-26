package lk.ijse.scaleShop.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.db.DBConnection;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class AdminDashBoardFormController {


    public AnchorPane pane;

    @FXML
    public void IncomeOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.INCOME_REPORT,pane);
    }
    @FXML
    public void BillOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.BILL,pane);
    }
    @FXML
    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }
    @FXML
    public void ProductOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PRODUCT,pane);
    }
    @FXML
    public void SupplierOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }
    @FXML
    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN,pane);
    }
    @FXML
    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACE_ORDER,pane);
    }

    public void btnCustomerReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/Customer.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }

    public void btnProdutReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/product Report.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }

    public void btnSupplierReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/Supplier.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }

    public void btnEmployeeReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/Employee.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }
}
