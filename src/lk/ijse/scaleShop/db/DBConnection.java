package lk.ijse.scaleShop.db;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); //Driver eka load kirima
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scaleshop", "root", "1234"); // mysql eka samaga connection eka
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return (null == dbConnection) ? new DBConnection() : dbConnection;
    }
    public Connection getConnection() {
        return connection;
    }

}

// ceparte exicution path eka
//String class ekak reprecent karana caractor ekak