package lk.ijse.scaleShop.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import lk.ijse.scaleShop.to.Product;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ProductFormController implements Initializable {
    public AnchorPane pane;
    public TableView tblProduct;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colQty;
    public TableColumn colContactNum;
    public TextField txtCode;
    public TextField txtType;
    public TextField txtDescription;
    public TextField txtPrice;
    public TextField txtQty;
    public JFXTextField txtSearch;
    public TableColumn colProductCode;
    public TableColumn colDescription;
    public TableColumn colPrice;

    ObservableList<Product> proList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        //Search bar
        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) ->{
                    loadAllProducts(newValue);
                });
        loadAllProducts("");
    }

    private void loadAllProducts(String text) {
        ObservableList<Product> proList = FXCollections.observableArrayList();

        try{
            ArrayList<Product> productsData =ProductModel.getProductData();
            for (Product product:productsData){
                if(product.getCode().contains(text) || product.getType().contains(text)){
                    Product p = new Product(product.getCode(), product.getType(), product.getDescription(), product.getPrice(), product.getQty());
                    proList.add(p);
                }
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        tblProduct.setItems(proList);
    }



    public void btnDeleteOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());


        try{
            Product product = new Product(code,type,description,price,qty);
            boolean isDeleted = ProductModel.delete(product,code);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Deleted Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Product not Deleted!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        int selectedID = tblProduct.getSelectionModel().getSelectedIndex();
        tblProduct.getItems().remove(selectedID);
    }

    public void btnSaveOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        String pattern="^(C0)([1-9]{1})([0-9]{0,})$";

        boolean isMatch= Pattern.matches(pattern,code);


        Product product = new Product(code,type,description,price,qty);
        try{
            boolean isAdded = ProductModel.save(product);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Product not Added!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        ObservableList<Product> products = tblProduct.getItems();
        products.add(product);
        tblProduct.setItems(products);


    }



    public void btnUpdateOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        try{
            Product product = new Product(code,type,description,price,qty);
            boolean isUpdated = ProductModel.update(product, code);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Product not Updated!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        ObservableList<Product> currentTableData = tblProduct.getItems();
        String currentProductCode = txtCode.getText();

        for(Product product : currentTableData){
            if(product.getCode() == currentProductCode){
                product.setType(txtType.getText());
                product.setDescription(txtDescription.getText());
                product.setPrice(Double.parseDouble(txtPrice.getText()));
                product.setQty(Integer.parseInt(txtQty.getText()));

                tblProduct.setItems(currentTableData);
                tblProduct.refresh();
                break;
            }
        }
    }

    public void txtCodeOnAction(ActionEvent event) {
        String code = txtCode.getText();
        try {
            Product product = ProductModel.search(code);
            if (product != null){
                fillData(product);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    private void fillData(Product product) {
        txtCode.setText(product.getCode());
        txtType.setText(product.getType());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtQty.setText(String.valueOf(product.getQty()));

    }

    public void btnClearOnAction(ActionEvent event) {
        txtCode.setText("");
        txtType.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtQty.setText("");

    }

    public void txtSearchOnAction(ActionEvent event) {
        String code = txtCode.getText();
        try {
            Product product = ProductModel.search(code);
            if (product != null){
                fillData(product);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
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

    public void rowClicked(MouseEvent mouseEvent) {
        Product clickedProduct = (Product) tblProduct.getSelectionModel().getSelectedItem();
        txtCode.setText(String.valueOf(clickedProduct.getCode()));
        txtType.setText(String.valueOf(clickedProduct.getType()));
        txtDescription.setText(String.valueOf(clickedProduct.getDescription()));
        txtPrice.setText(String.valueOf(clickedProduct.getPrice()));
        txtQty.setText(String.valueOf(clickedProduct.getQty()));

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

    public void ProductOnAction(ActionEvent event) {
    }

    public void SupplierOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    public void placeOrderOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PLACE_ORDER,pane);
    }


}
