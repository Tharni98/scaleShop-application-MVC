package lk.ijse.scaleShop.model;

import lk.ijse.scaleShop.to.CartDetail;
import lk.ijse.scaleShop.to.Product;
import lk.ijse.scaleShop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {


    public static ArrayList<Product> getProductData() throws SQLException, ClassNotFoundException {
        ArrayList<Product> productsData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT * FROM Product ORDER BY CAST(SUBSTRING(code, 2) AS UNSIGNED)");
        while (rs.next()) {
            productsData.add(new Product(rs.getString("code"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("unitPrice"),
                    rs.getInt("QuntityOnHand")
            ));
        }
        return productsData;
    }

    public static boolean save(Product product) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, product.getCode(), product.getType(), product.getDescription(), product.getPrice(), product.getQty());
    }

    public static boolean update(Product product, String code) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Product SET type=?, description=?, unitPrice=?, QuntityOnHand=? WHERE code=?";
        return CrudUtil.execute(sql, product.getType(), product.getDescription(), product.getPrice(), product.getQty(), code);
    }

    public static Product search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Product WHERE code = ?";
        ResultSet result = CrudUtil.execute(sql, code);

        if (result.next()) {
            return new Product(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)
            );
        }
        return null;
    }

    public static boolean delete(Product product, String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Product WHERE code=?";
        return CrudUtil.execute(sql, product.getCode());
    }

    public static boolean updateQty(ArrayList<CartDetail> cartDetails) throws SQLException, ClassNotFoundException {
        for (CartDetail cartDetail : cartDetails) {
            if (!updateQty(new Product(cartDetail.getCode(), cartDetail.getDescription(), cartDetail.getUnitPrice(), cartDetail.getQty()))) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(Product product) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Product SET qtyOnHand = qtyOnHand - ? WHERE code = ?";
        return CrudUtil.execute(sql, product.getQty(), product.getCode());
    }

    public static ArrayList<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM Product";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> codeList = new ArrayList<>();

        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM product";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
}
