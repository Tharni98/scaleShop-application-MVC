package lk.ijse.scaleShop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case LOGIN:
                window.setTitle("LoginForm ");
                initUI("LoginForm.fxml");
                break;
            case ADMIN_DASHBOARD:
                initUI("AdminDashBoardForm.fxml");
                break;
            case BILL:
                window.setTitle("BillForm");
                initUI("BillForm.fxml");
                break;
            case CUSTOMER:
                window.setTitle("CustomerForm");
                initUI("CustomerForm.fxml");
                break;
            case INCOME_REPORT:
                window.setTitle("IncomeReport");
                initUI("IncomeReportForm.fxml");
                break;
            case PRODUCT:
                window.setTitle("ProductForm");
                initUI("ProductForm.fxml");
                break;
            case SUPPLIER:
                window.setTitle("SupplierForm");
                initUI("SupplierForm.fxml");
                break;

            case PLACE_ORDER:
                window.setTitle("PlaceOrder");
                initUI("PlaceOrderForm.fxml");
                break;

            case OWNER_DASHBOARD:
                window.setTitle("OwnerDashboard");
                initUI("OwnerDashBoardForm.fxml");
                break;
            case OWNER_PRODUCT:
                window.setTitle("OwnerProductForm");
                initUI("OwnerProductForm.fxml");
                break;
            case OWNER_INCOME:
                window.setTitle("OwnerIncomeReport");
                initUI("OwnerIncomeForm.fxml");
                break;
            case OWNER_BILL:
                window.setTitle("OwnerBillForm");
                initUI("OwnerBillForm.fxml");
                break;
            case OWNER_EMPLOYEE:
                window.setTitle("OwnerEmployeeForm");
                initUI("OwnerEmployeeForm.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }

    }
    private static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class
                .getResource("/lk/ijse/scaleShop/view/" + location)));
    }
}

