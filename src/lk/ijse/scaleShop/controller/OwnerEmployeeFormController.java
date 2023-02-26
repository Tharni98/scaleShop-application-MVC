package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.db.DBConnection;
import lk.ijse.scaleShop.model.OwnerEmployeeModel;
import lk.ijse.scaleShop.to.OwnerEmployee;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class OwnerEmployeeFormController implements Initializable {

    public AnchorPane pane;
    public TextField txtEmployeeId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView tblEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colJobRole;
    public TableColumn colSalary;
    public TextField txtJobRole;
    public JFXTextField txtSearch;
    public Label lblNameWarning;

    ObservableList<OwnerEmployee> cusList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        //Search bar
        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) ->{
                    loadAllEmployees(newValue);
                });
        loadAllEmployees("");
    }

    private void loadAllEmployees(String text) {
        ObservableList<OwnerEmployee> cusList = FXCollections.observableArrayList();

        try{
            ArrayList<OwnerEmployee> employeesData = OwnerEmployeeModel.getCustomerData();
            for (OwnerEmployee employee:employeesData){
                if(employee.getEmpId().contains(text) || employee.getName().contains(text)){
                    OwnerEmployee e = new OwnerEmployee(employee.getEmpId(), employee.getName(), employee.getAddress(), employee.getSalary(),employee.getRole());
                    cusList.add(e);
                }
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

        tblEmployee.setItems(cusList);
    }



    public void DeleteOnAction(ActionEvent event) {
        String empID = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Double salary = Double.parseDouble(txtSalary.getText());
        String role =txtJobRole.getText();

        try{
            OwnerEmployee customer = new OwnerEmployee(empID,name,address,salary,role);
            boolean isDeleted = OwnerEmployeeModel.delete(customer, empID);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Employee not Deleted!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        int selectedID = tblEmployee.getSelectionModel().getSelectedIndex();
        tblEmployee.getItems().remove(selectedID);
    }

    public void SaveOnAction(ActionEvent event) {
        String empId = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String role=txtJobRole.getText();

        /*String pattern="^(E0)([1-9]{1})([0-9]{0,})$";

        boolean isMatch= Pattern.matches(pattern,empId);*/

        OwnerEmployee employee = new OwnerEmployee(empId,name,address,salary,role);
        try{
            boolean isAdded = OwnerEmployeeModel.save(employee);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Employee not Added!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        ObservableList<OwnerEmployee> employees = tblEmployee.getItems();
        employees.add(employee);
        tblEmployee.setItems(employees);

    }

    public void txtIDOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        try {
           OwnerEmployee employee = OwnerEmployeeModel.search(id);
            if (employee != null){
                fillData(employee);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        Pattern pattern= Pattern.compile("^(C0)([1-9]{1})([0-9]{0,})$");
        Matcher matcher=pattern.matcher(txtEmployeeId.getText());

        boolean isMatches =matcher.matches();
        if (isMatches){
            txtName.clear();
            txtName.requestFocus();
            lblNameWarning.setText("Invalid Input");

        }else {
           lblNameWarning.setText("");
        }
    }

    public void txtSearchOnAction(ActionEvent event) {
        String id = txtSearch.getText();
        try{
            OwnerEmployee employee = OwnerEmployeeModel.search(id);
            if (employee != null){
                fillData(employee);
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String empId = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
       Double salary = Double.parseDouble(txtSalary.getText());
       String role = txtJobRole.getText();

        try{
            OwnerEmployee employee = new OwnerEmployee(empId,name,address,salary,role);
            boolean isUpdated = OwnerEmployeeModel.update(employee, empId);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Employee not Updated!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }


        ObservableList<OwnerEmployee> currentTableData = tblEmployee.getItems();
        String currentEmployeeID = txtEmployeeId.getText();

        for(OwnerEmployee employee : currentTableData){
            if(employee.getEmpId() == currentEmployeeID){
                employee.setName(txtName.getText());
                employee.setAddress(txtAddress.getText());
                employee.setSalary(Double.parseDouble(txtSalary.getText()));

                tblEmployee.setItems(currentTableData);
                tblEmployee.refresh();
                break;
            }
        }
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtAddress.setText("");
       txtSalary.setText("");
       txtJobRole.setText("");
    }

    private void fillData(OwnerEmployee employee){
        txtEmployeeId.setText(employee.getEmpId());
        txtName.setText(employee.getName());
        txtAddress.setText(employee.getAddress());
        txtSalary.setText(String.valueOf(employee.getSalary()));
        txtJobRole.setText(employee.getRole());
    }


    public void rowClicked(MouseEvent mouseEvent) {
        OwnerEmployee clickedEmployee = (OwnerEmployee) tblEmployee.getSelectionModel().getSelectedItem();
        txtEmployeeId.setText(String.valueOf(clickedEmployee.getEmpId()));
        txtName.setText(String.valueOf(clickedEmployee.getName()));
        txtAddress.setText(String.valueOf(clickedEmployee.getAddress()));
        txtSalary.setText(String.valueOf(clickedEmployee.getSalary()));
        txtJobRole.setText(String.valueOf(clickedEmployee.getRole()));
    }
    public void btnReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/Employee.jrxml");
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
        Navigation.navigate(Routes.OWNER_PRODUCT,pane);
    }


}
