package lk.ijse.scaleShop.controller;


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
import lk.ijse.scaleShop.model.CustomerModel;
import lk.ijse.scaleShop.model.ProductModel;
import lk.ijse.scaleShop.to.Customer;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController implements Initializable {


    public AnchorPane pane;
    public TextField txtCustomerId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContactNo;
    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colName;
    
    public TableColumn colNumber;
    public TableColumn colEmployeeId;

    public TextField txtSearch;
    public TextField txtEmployeeId;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colEmployee;


    ObservableList<Customer> cusList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("number"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("empId"));

        //Search bar
        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) ->{
                    loadAllCustomers(newValue);
                });
        loadAllCustomers("");
    }

    private void loadAllCustomers(String text) {
        ObservableList<Customer> cusList = FXCollections.observableArrayList();

        try{
            ArrayList<Customer> customersData = CustomerModel.getCustomerData();
            for (Customer customer:customersData){
                if(customer.getCustId().contains(text) || customer.getName().contains(text)){
                    Customer c = new Customer(customer.getCustId(), customer.getName(), customer.getAddress(), customer.getNumber(),customer.getEmpId());
                    cusList.add(c);
                }
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        tblCustomer.setItems(cusList);
    }



    public void DeleteOnAction(ActionEvent event) {
        String custId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        int  number = Integer.parseInt(txtContactNo.getText());
        String empId= txtEmployeeId.getText();



        try{
            Product product = new Product(custId,name,address,number,empId);
            boolean isDeleted = ProductModel.delete(product,custId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Customer not Deleted!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        int selectedID = tblCustomer.getSelectionModel().getSelectedIndex();
        tblCustomer.getItems().remove(selectedID);
    }

    public void SaveOnAction(ActionEvent event) {
        String custId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        int number = Integer.parseInt(txtContactNo.getText());
        String empId = txtEmployeeId.getText();

        /*String pattern="^(C0)([1-9]{1})([0-9]{0,})$";

        boolean isMatch= Pattern.matches(pattern,custId);*/

        Customer customer = new Customer(custId,name,address,number,empId);
        try{
            boolean isAdded = CustomerModel.save(customer);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Customer not Added!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        ObservableList<Customer> customers = tblCustomer.getItems();
        customers.add(customer);
        tblCustomer.setItems(customers);
        System.out.println(customers);
    }

    public void txtCustomerIdOnAction(ActionEvent event) {
        String id = txtSearch.getText();
        try{
            Customer customer = CustomerModel.search(id);
            if (customer != null){
                fillData(customer);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void rowClicked(MouseEvent mouseEvent) {
        Customer clickedCustomer = (Customer) tblCustomer.getSelectionModel().getSelectedItem();
        txtCustomerId.setText(String.valueOf(clickedCustomer.getCustId()));
        txtName.setText(String.valueOf(clickedCustomer.getName()));
        txtAddress.setText(String.valueOf(clickedCustomer.getAddress()));
        txtContactNo.setText(String.valueOf(clickedCustomer.getNumber()));
        txtEmployeeId.setText(String.valueOf(clickedCustomer.getEmpId()));
    }



    public void UpdateOnAction(ActionEvent event) {
        String custId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        int number = Integer.parseInt(txtContactNo.getText());
        String empId=txtEmployeeId.getText();

        try{
            Customer customer = new Customer(custId,name,address,number,empId);
            boolean isUpdated = CustomerModel.update(customer, custId);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Customer not Updated!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }


        ObservableList<Customer> currentTableData = tblCustomer.getItems();
        String currentCustomerId = txtCustomerId.getText();

        for(Customer customer : currentTableData){
            if(customer.getCustId() == currentCustomerId){
                customer.setName(txtName.getText());
                customer.setAddress(txtAddress.getText());
                customer.setNumber(Integer.parseInt(txtContactNo.getText()));
                customer.setEmpId(txtEmployeeId.getText());

                tblCustomer.setItems(currentTableData);
                tblCustomer.refresh();
                break;
            }
        }

    }
    public void ClearOnAction(ActionEvent event) {
        txtCustomerId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNo.setText("");
        txtEmployeeId.setText("");
    }
    private void fillData(Customer customer){
        txtCustomerId.setText(customer.getCustId());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtContactNo.setText(String.valueOf(customer.getNumber()));
        txtEmployeeId.setText(customer.getEmpId());

    }
    public void btnReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/Customer.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
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

    public void CustomerOnAction(ActionEvent event) {

    }

    public void ProductOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PRODUCT,pane);
    }

    public void SupplierOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    public void placeOrderOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PLACE_ORDER,pane);
    }


}
