package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXTextField;
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
import lk.ijse.scaleShop.model.OwnerProductModel;
import lk.ijse.scaleShop.to.OwnerProduct;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


public class OwnerProductFormController  implements Initializable {

    public AnchorPane pane;
    public TextField txtCode;
    public TextField txtType;
    public TextField txtDescription;
    public TextField txtPrice;
    public TextField txtQty;

    public JFXTextField txtSearch;
    public TableColumn colCode;
    public TableColumn colProductType;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableView tblOwnerProduct;

    ObservableList<OwnerProduct> cusList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        //Search bar
        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) ->{
                    loadAllCustomers(newValue);
                });
        loadAllCustomers("");
    }

    private void loadAllCustomers(String text) {
        ObservableList<OwnerProduct> cusList = FXCollections.observableArrayList();

        try{
            ArrayList<OwnerProduct> ownerProductsData = OwnerProductModel.getOwnerProductsData();
            for (OwnerProduct ownerProduct:ownerProductsData){
                if(ownerProduct.getCode().contains(text) || ownerProduct.getType().contains(text)){
                    OwnerProduct OP = new OwnerProduct(ownerProduct.getCode(), ownerProduct.getType(), ownerProduct.getDescription(), ownerProduct.getPrice(),ownerProduct.getQty());
                    cusList.add(OP);
                }
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        tblOwnerProduct.setItems(cusList);
    
    }


    public void DeleteOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        Double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        try{
            OwnerProduct ownerProduct = new OwnerProduct(code,type,description,price,qty);
            boolean isDeleted = OwnerProductModel.delete(ownerProduct,code);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Deleted Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Product not Deleted!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        int selectedcode = tblOwnerProduct.getSelectionModel().getSelectedIndex();
        tblOwnerProduct.getItems().remove(selectedcode);


    }

    public void SaveOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        double price=Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        String pattern="^(P0)([1-9]{1})([0-9]{0,})$";

        boolean isMatch= Pattern.matches(pattern,code);

        OwnerProduct ownerProduct = new OwnerProduct(code,type,description,price,qty);
        try{
            boolean isAdded = OwnerProductModel.save(ownerProduct);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Product not Added!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        ObservableList<OwnerProduct> ownerProducts = tblOwnerProduct.getItems();
        ownerProducts.add(ownerProduct);
        tblOwnerProduct.setItems(ownerProducts);


    }

    public void txtCodeOnAction(ActionEvent event) {
        String code = txtCode.getText();
        try {
            OwnerProduct ownerProduct = OwnerProductModel.search(code);
            if (ownerProduct != null){
                fillData(ownerProduct);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }


    }

    public void rowClicked(MouseEvent mouseEvent) {
        OwnerProduct clickedCustomer = (OwnerProduct) tblOwnerProduct.getSelectionModel().getSelectedItem();
        txtCode.setText(String.valueOf(clickedCustomer.getCode()));
        txtType.setText(String.valueOf(clickedCustomer.getType()));
        txtDescription.setText(String.valueOf(clickedCustomer.getDescription()));
        txtPrice.setText(String.valueOf(clickedCustomer.getPrice()));
        txtPrice.setText(String.valueOf(clickedCustomer.getPrice()));
        txtQty.setText(String.valueOf(clickedCustomer.getQty()));

    }



    public void btnUpdateOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        double price=Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        try{
            OwnerProduct ownerProduct = new OwnerProduct(code,type,description,price,qty);
            boolean isUpdated = OwnerProductModel.update(ownerProduct, code);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Product not Updated!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }


        ObservableList<OwnerProduct> currentTableData = tblOwnerProduct.getItems();
        String currentOwnerProductCode = txtCode.getText();

        for(OwnerProduct ownerProduct : currentTableData){
            if(ownerProduct.getCode() == currentOwnerProductCode){
                ownerProduct.setType(txtType.getText());
                ownerProduct.setDescription(txtDescription.getText());
                ownerProduct.setPrice(Double.parseDouble(txtPrice.getText()));
                ownerProduct.setQty(Integer.parseInt(txtQty.getText()));

                tblOwnerProduct.setItems(currentTableData);
                tblOwnerProduct.refresh();
                break;
            }

        }

    }

        public void txtSearchOnAction(ActionEvent event) {
            String code = txtSearch.getText();
            try{
                OwnerProduct ownerProduct = OwnerProductModel.search(code);
                if (ownerProduct != null){
                    fillData(ownerProduct);
                }
            }catch (SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }



    private void fillData(OwnerProduct ownerProduct) {
            txtCode.setText(ownerProduct.getCode());
            txtType.setText(ownerProduct.getType());
            txtDescription.setText(ownerProduct.getDescription());
            txtPrice.setText(String.valueOf(ownerProduct.getPrice()));
            txtQty.setText(String.valueOf(ownerProduct.getPrice()));

    }

    public void btnReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/product Report.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }

    public void btnClearOnAction(ActionEvent event) {
        txtCode.setText("");
        txtType.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtQty.setText("");
    }
    public void LogoutOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.LOGIN,pane);
    }

    public void DashboardOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_DASHBOARD,pane);
    }

    public void IncomeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_INCOME,pane);
    }

    public void BillOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_BILL,pane);
    }

    public void EmployeeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.OWNER_EMPLOYEE,pane);
    }

    public void ProductOnAction(ActionEvent event) throws IOException {

    }


}


