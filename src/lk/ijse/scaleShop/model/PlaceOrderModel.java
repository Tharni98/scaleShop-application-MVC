package lk.ijse.scaleShop.model;

import lk.ijse.scaleShop.db.DBConnection;
import lk.ijse.scaleShop.to.Order;
import lk.ijse.scaleShop.to.PlaceOrder;
import lk.ijse.scaleShop.to.Product;
import lk.ijse.scaleShop.util.CrudUtil;
import lk.ijse.scaleShop.view.tm.PlaceOrderTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    public static boolean placeOrder(PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            String date= String.valueOf(LocalDate.now());
            String orderSql= "insert into orders values(?,?,?)";
            boolean isOrderInserted= CrudUtil.execute(orderSql,date,placeOrder.getOrderId(),placeOrder.getCustomerId());

            if(isOrderInserted){
                int num=0;

                for (PlaceOrderTM orderDetail: placeOrder.getOrderDetails()) {
                    String orderDetailSql="insert into orderdetail values(?,?,?,?,?)";
                    boolean isOrderDetailsInserted= CrudUtil.execute(orderDetailSql,orderDetail.getCode(),placeOrder.getOrderId(),orderDetail.getQty(),orderDetail.getUnitPrice(),orderDetail.getTotal());
                    if(isOrderDetailsInserted){
                        num++;
                    }
                }

                if(num==placeOrder.getOrderDetails().size()){
                    int number=0;

                    for (PlaceOrderTM product: placeOrder.getOrderDetails()) {
                        String productSql="update product set QuntityOnHand=QuntityOnHand-? where code=?";
                        boolean isOrderDetailsInserted= CrudUtil.execute(productSql, product.getQty(),product.getCode());
                        if(isOrderDetailsInserted){
                            number++;
                        }
                    }

                    if(number==placeOrder.getOrderDetails().size()){
                        return true;
                    }

                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }

    }
}
