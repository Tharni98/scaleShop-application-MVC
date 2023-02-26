package lk.ijse.scaleShop.model;

import lk.ijse.scaleShop.to.Order;
import lk.ijse.scaleShop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    public static boolean save(Order order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders VALUES(?, ?, ?)";
        return CrudUtil.execute(sql, order.getOrderId(), order.getDate(), order.getCustomerId());
    }

    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return generateNextOrderId(result.getString(1));
        }
        return generateNextOrderId(result.getString(null));
    }

    private static String generateNextOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O0");
            int id = Integer.parseInt(split[1]);
            id += 1;
            return "O0" + id;
        }
        return "O01";

    }
}
