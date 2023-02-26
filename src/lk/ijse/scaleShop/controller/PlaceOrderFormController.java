package lk.ijse.scaleShop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scaleShop.db.DBConnection;
import lk.ijse.scaleShop.model.CustomerModel;
import lk.ijse.scaleShop.model.OrderModel;
import lk.ijse.scaleShop.model.PlaceOrderModel;
import lk.ijse.scaleShop.model.ProductModel;
import lk.ijse.scaleShop.to.CartDetail;
import lk.ijse.scaleShop.to.Customer;
import lk.ijse.scaleShop.to.PlaceOrder;
import lk.ijse.scaleShop.to.Product;
import lk.ijse.scaleShop.util.Navigation;
import lk.ijse.scaleShop.util.Routes;
import lk.ijse.scaleShop.view.tm.OrderDetailsTm;
import lk.ijse.scaleShop.view.tm.PlaceOrderTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class PlaceOrderFormController  {


    public Label lblQty;
    public Label lblUnitPrice;
    public Label lblDescription;
    public Label lblCode;
    public Label lblOrderDate;
    public Label lblOrderId;
    public JFXTextField txtSearch;
    public TableColumn colTotal;
    public TableColumn colUnitPrice;
    public TableColumn colCode;
    public TableView<PlaceOrderTM> tblPlaceOrder;
    public ComboBox cmbCustomerId;
    public TextField txtQty;
    public ComboBox cmbCode;
    public AnchorPane pane;
    public Label lblCustName;
    public TableColumn colName;
    public TableColumn colqty;
    public TableColumn colTot;

    public void initialize(){
        loadCustomerIds();
        loadNextOrderId();
        loadProductIds();
        loadOrderDate();
        setCellValueFactory();

    }

    private void loadOrderDate() {
        lblOrderDate.setText(String.valueOf((LocalDate.now())));
    }

    public void placeOrderOnAction(ActionEvent event) {
    }

    public void SupplierOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    public void ProductOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.PRODUCT,pane);
    }

    public void CustomerOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }

    public void BillOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.BILL,pane);
    }

    public void IncomeOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.INCOME_REPORT,pane);
    }

    public void DashboardOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ADMIN_DASHBOARD,pane);
    }

    public void PlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String customerId = String.valueOf(cmbCustomerId.getValue());

        PlaceOrder placeOrder = new PlaceOrder(customerId, orderId, arrayList);
        try {
            boolean isPlaced = PlaceOrderModel.placeOrder(placeOrder);
            if (isPlaced) {
                obList.clear();
                loadNextOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        lblQty.setText("");
    }

    public void btnNewCustomerOnAction(ActionEvent event) {
    }

    public void txtQtyOnAction(ActionEvent event) {
    }

    public void cmbCustomerOnAction(ActionEvent event) {
        String code = String.valueOf(cmbCustomerId.getValue());
        try {
            Customer cust = CustomerModel.search(code);
            fillCustFields(cust);
            txtQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillCustFields(Customer customer) {
        lblCustName.setText(customer.getName());

    }


    public void cmbCode(ActionEvent event) {
        String code = String.valueOf(cmbCode.getValue());
        try {
            Product product = ProductModel.search(code);
            fillItemFields(product);
            txtQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Product product) {
        lblDescription.setText(product.getDescription());
        lblUnitPrice.setText(String.valueOf(product.getPrice()));
        lblQty.setText(String.valueOf(product.getQty()));
    }

    public void LogoutOnAction(ActionEvent event) {
    }
    private void loadCustomerIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = CustomerModel.loadCustomerIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbCustomerId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProductIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = ProductModel.loadCustomerIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbCode.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadNextOrderId() {
        try {
            String orderId = OrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static ArrayList<PlaceOrderTM> arrayList=new ArrayList<>();
    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    public void btnAddToCartOnAction(ActionEvent event) {
        String code = String.valueOf(cmbCode.getValue());
        String description = lblDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = unitPrice * qty;

        txtQty.setText("");
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQty.setText("");

        PlaceOrderTM placeOrder =new PlaceOrderTM(code,description,qty,unitPrice,total);

        int num= -1;
        for (int i =0; i<arrayList.size();i++){
            if(arrayList.get(i).getCode().equals(placeOrder.getCode())){
                num=i;
            }
        }

        setCellValueFactory();

        if(num!=-1){
           qty= arrayList.get(num).getQty()+qty;

           total=qty*unitPrice;
            arrayList.remove(num);

            PlaceOrderTM newPlaceOrder=new PlaceOrderTM(code,description,qty,unitPrice,total);
            arrayList.add(newPlaceOrder);
            obList = FXCollections.observableArrayList(arrayList);
            tblPlaceOrder.setItems(obList);
        }else {
            arrayList.add(placeOrder);
            obList = FXCollections.observableArrayList(arrayList);
            tblPlaceOrder.setItems(obList);
        }

    }
    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colName.setCellValueFactory(new PropertyValueFactory("description"));
        colqty.setCellValueFactory(new PropertyValueFactory("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colTot.setCellValueFactory(new PropertyValueFactory("total"));

    }

    public void btnRemoveOnAction(ActionEvent event) {
        int num =0;

        for (int i =0; i<arrayList.size();i++){
            if(arrayList.get(i).getCode().equals(code)){
                num=i;
            }
        }

        arrayList.remove(num);
        obList = FXCollections.observableArrayList(arrayList);
        tblPlaceOrder.setItems(obList);
    }

    public void btnRemoveAll(ActionEvent event) {
        arrayList.removeAll(arrayList);
        obList = FXCollections.observableArrayList(arrayList);
        tblPlaceOrder.setItems(obList);
    }

    static  String code;
    public void rowClicked(MouseEvent mouseEvent) {
        PlaceOrderTM orderTM= tblPlaceOrder.getSelectionModel().getSelectedItem();
        code=orderTM.getCode();
    }

    public void btnReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/scaleShop/view/report/PlaceOrderReport.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }
}
