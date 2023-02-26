package lk.ijse.scaleShop.model;

import lk.ijse.scaleShop.to.Customer;
import lk.ijse.scaleShop.util.CrudUtil;
import lk.ijse.scaleShop.view.tm.OrderDetailsTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static ArrayList<Customer> getCustomerData() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customersData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer ORDER BY CAST(SUBSTRING(customerID, 2) AS UNSIGNED)");
        while (rs.next()){
            customersData.add(new Customer(rs.getString("customerID"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getInt("contactNo"),
                    rs.getString("employeeID")));
        }
        return customersData;
    }
    public static boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, customer.getCustId(), customer.getName(), customer.getAddress(), customer.getNumber(),customer.getEmpId());
    }

    public static Customer search(String custId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customerID = ?";
        ResultSet result = CrudUtil.execute(sql,custId);

        if(result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5)
            );
        }
        return null;
    }
    public static boolean update (Customer customer, String custId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET name=?, address=?,  contactNo=? , employeeID=? WHERE customerID=?";
        return CrudUtil.execute(sql, customer.getName(), customer.getAddress(), customer.getNumber(),customer.getEmpId(),custId);
    }
    public static boolean delete (Customer customer, String custId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE customerID=?";
        return CrudUtil.execute(sql,customer.getCustId());
    }

    public static ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT customerID FROM Customer";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
//    public static ArrayList<OrderDetailsTm> loadCustomerIds() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT customerID FROM Customer";
//        ResultSet result = CrudUtil.execute(sql);
//
//        ArrayList<String> idList = new ArrayList<>();
//
//        while (result.next()) {
//            idList.add(result.getString(1));
//        }
//        return idList;
//    }
}
