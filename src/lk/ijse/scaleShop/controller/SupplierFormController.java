package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.db.DBConnection;
import lk.ijse.scaleShop.model.ProductModel;
import lk.ijse.scaleShop.model.SupplierModel;
import lk.ijse.scaleShop.to.Product;
import lk.ijse.scaleShop.to.Supplier;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.print.attribute.standard.MediaSize;

public class SupplierFormController implements Initializable {


    public AnchorPane pane;
    public TextField txtId;
    public TextField txtName;
    public TextField txtType;
    public TextField txtQuantity;
    public TableView tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colType;
    public TableColumn colQty;
    public TableColumn colNumber;
    public TextField txtNo;
    public TextField txtSearch;

    ObservableList<Supplier> cusList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        //Search bar
        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) ->{
                    loadAllSupplier(newValue);
                });
        loadAllSupplier("");
    }

    private void loadAllSupplier(String text) {
        ObservableList<Supplier> cusList = FXCollections.observableArrayList();

        try{
            ArrayList<Supplier> suppliersData = SupplierModel.getSupplierData();
            for (Supplier supplier:suppliersData){
                if(supplier.getId().contains(text) || supplier.getName().contains(text)){
                    Supplier s = new Supplier(supplier.getId(), supplier.getName(), supplier.getNumber(), supplier.getType(),supplier.getQty());
                    cusList.add(s);
                }
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        tblSupplier.setItems(cusList);
    }



    public void DeleteOnAction(ActionEvent event) {
        int selectedID = tblSupplier.getSelectionModel().getSelectedIndex();
        tblSupplier.getItems().remove(selectedID);

    }
    public void SaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        int number = Integer.parseInt(txtNo.getText());
        String type = txtType.getText();
        int qty = Integer.parseInt(txtQuantity.getText());

        String pattern="^(S0)([1-9]{1})([0-9]{0,})$";

        boolean isMatch= Pattern.matches(pattern,id);

        Supplier supplier = new Supplier(id,name,number,type,qty);
        try{
            boolean isAdded = SupplierModel.save(supplier);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Supplier not Added!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        ObservableList<Supplier> suppliers = tblSupplier.getItems();
        suppliers.add(supplier);
        tblSupplier.setItems(suppliers);
    }



    public void rowClicked(MouseEvent mouseEvent) {
       Supplier clickedSupplier = (Supplier) tblSupplier.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(clickedSupplier.getId()));
        txtName.setText(String.valueOf(clickedSupplier.getName()));
        txtNo.setText(String.valueOf(clickedSupplier.getNumber()));
        txtType.setText(String.valueOf(clickedSupplier.getType()));
        txtQuantity.setText(String.valueOf(clickedSupplier.getQty()));
    }


    public void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        int number = Integer.parseInt(txtNo.getText());
        String type = txtType.getText();
        int qty = Integer.parseInt(txtQuantity.getText());


        try {
            Supplier supplier= new Supplier(id, name, number, type, qty);
            boolean isUpdated = SupplierModel.update(supplier, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Supplier not Updated!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Supplier> currentTableData = tblSupplier.getItems();
        String currentSupplierId = txtId.getText();

        for(Supplier supplier : currentTableData){
            if(supplier.getId() == currentSupplierId){
                supplier.setName(txtName.getText());
                supplier.setNumber(Integer.parseInt(txtNo.getText()));
                supplier.setType(txtType.getText());
                supplier.setQty(Integer.parseInt(txtQuantity.getText()));

               tblSupplier.setItems(currentTableData);
               tblSupplier.refresh();
                break;
            }
        }
    }

    public void btnClearOnAction(ActionEvent event) {
        txtId.setText("");
        txtName.setText("");
        txtNo.setText("");
        txtType.setText("");
        txtQuantity.setText("");
    }

    public void btnReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/Supplier.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }

    public void txtIdOnAction(ActionEvent event) {
        String id = txtSearch.getText();
        try{
            Supplier supplier = SupplierModel.search(id);
            if (supplier != null){
                fillData(supplier);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    private void fillData(Supplier supplier){
        txtId.setText(supplier.getId());
        txtName.setText(supplier.getName());
        txtNo.setText(String.valueOf(supplier.getNumber()));
        txtType.setText(supplier.getType());
        txtQuantity.setText(String.valueOf(supplier.getQty()));
    }

    public void txtSearchOnAction(ActionEvent event) {
        String id = txtSearch.getText();
        try{
            Supplier supplier = SupplierModel.search(id);
            if (supplier != null){
                fillData(supplier);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void LogoutOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.LOGIN,pane);
    }

    public void DashboardOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ADMIN_DASHBOARD,pane);
    }

    public void IncomeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.INCOME_REPORT,pane);
    }

    public void BillOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.BILL,pane);
    }

    public void CustomerOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }

    public void ProductOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PRODUCT,pane);
    }

    public void SupplierOnAction(ActionEvent event) {
    }

    public void placeOrderOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PLACE_ORDER,pane);
    }


}
