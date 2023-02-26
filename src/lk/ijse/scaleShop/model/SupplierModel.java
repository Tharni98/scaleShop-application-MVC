package lk.ijse.scaleShop.model;

import lk.ijse.scaleShop.to.Product;
import lk.ijse.scaleShop.to.Supplier;
import lk.ijse.scaleShop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    public static ArrayList<Supplier> getSupplierData() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliersData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT * FROM Supplier ORDER BY CAST(SUBSTRING(supplierID, 2) AS UNSIGNED)");
        while (rs.next()){
           suppliersData.add(new Supplier(rs.getString("supplierID"),
                    rs.getString("name"),
                    rs.getInt("contactNo"),
                   rs.getString("type"),
                    rs.getInt("Quntity")));
        }
        return suppliersData;
    }
    public static boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Supplier VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, supplier.getId(), supplier.getName(), supplier.getNumber(), supplier.getType(), supplier.getQty());
    }

    public static boolean update (Supplier supplier, String id) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Supplier SET name=?, contactNo=?, type=?, Quntity=? WHERE  supplierID=?";
        return CrudUtil.execute(sql, supplier.getName(), supplier.getNumber(), supplier.getType(), supplier.getQty(),id);

    }
    public static Supplier search(String empId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Supplier WHERE supplierID = ?";
       ResultSet result = CrudUtil.execute(sql,empId);

        if(result.next()){
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4),
                    result.getInt(5)
            );
        }
        return null;
    }
}
