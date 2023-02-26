package lk.ijse.scaleShop.model;

import lk.ijse.scaleShop.to.OwnerProduct;
import lk.ijse.scaleShop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OwnerProductModel {
    public static ArrayList<OwnerProduct> getOwnerProductsData() throws SQLException, ClassNotFoundException {
        ArrayList<OwnerProduct> OwnerProductsData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT * FROM Product ORDER BY CAST(SUBSTRING(code, 2) AS UNSIGNED)");
        while (rs.next()){
            OwnerProductsData.add(new OwnerProduct(rs.getString("code"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("unitPrice"),
                    rs.getInt(" QuntityOnHand")
            ));
        }
        return OwnerProductsData;
    }
    public static boolean save(OwnerProduct ownerProduct) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, ownerProduct.getCode(), ownerProduct.getType(), ownerProduct.getDescription(), ownerProduct.getPrice(), ownerProduct.getQty());
    }

    public static boolean update (OwnerProduct ownerProduct, String code) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Product SET type=?, description=?, unitPrice=?, QuntityOnHand=? WHERE code=?";
        return CrudUtil.execute(sql, ownerProduct.getType(), ownerProduct.getDescription(), ownerProduct.getPrice(), ownerProduct.getQty(), code);
    }

    public static OwnerProduct search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Product WHERE code = ?";
        ResultSet result = CrudUtil.execute(sql,code);

        if(result.next()){
            return new OwnerProduct(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)
            );
        }
        return null;
    }

    public static boolean delete (OwnerProduct product, String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Product WHERE code=?";
        return CrudUtil.execute(sql,product.getCode());
    }
}
