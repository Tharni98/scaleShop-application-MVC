package lk.ijse.scaleShop.util;

import lk.ijse.scaleShop.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    //DATATYPE EKA DANNE NATHTAM GENARICS
    //STATEMANT-SQL QUARY EXECUTE
    //PS -DINAMIC QURY EXICUTE
    //wraper -PRIMITIVE DATA TYPE EKAK Object represntation
    //PARAMETER GANA HARIYAT DANNE NATHTHAM VARGS USE KARAI

   public static <T>T execute(String sql, Object...args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        //vrg.lenth eka
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1), args[i]);
        }

        if(sql.startsWith("SELECT") || sql.startsWith("select")) {
            return (T) pstm.executeQuery();     //RESALT SET RETURN
        }
        return (T)(Boolean)(pstm.executeUpdate()>0);    //INTIGER VALUE
    }

}

