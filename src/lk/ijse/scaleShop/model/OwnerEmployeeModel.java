package lk.ijse.scaleShop.model;

import java.sql.ResultSet;
import lk.ijse.scaleShop.to.OwnerEmployee;
import lk.ijse.scaleShop.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OwnerEmployeeModel {
    public static ArrayList<OwnerEmployee> getCustomerData() throws SQLException, ClassNotFoundException {
        ArrayList<OwnerEmployee> OwnerEmployeeData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT * FROM  Employee ORDER BY CAST(SUBSTRING(employeeID, 2) AS UNSIGNED)");
        while (rs.next()){
            OwnerEmployeeData.add(new OwnerEmployee(rs.getString("employeeID"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getDouble("salary"),
                    rs.getString("jobRole")));
        }
        return OwnerEmployeeData;
    }

    public static boolean save(OwnerEmployee employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, employee.getEmpId(), employee.getName(), employee.getAddress(), employee.getSalary(),employee.getRole());
    }
    public static OwnerEmployee search(String empId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE employeeID = ?";
        ResultSet result = CrudUtil.execute(sql,empId);

        if(result.next()){
            return new OwnerEmployee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getString(5)
            );
        }
        return null;
    }
    public static boolean update(OwnerEmployee employee, String empId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Employee SET name=?, address=?, salary=?, jobRole=? WHERE employeeID=?";
        return CrudUtil.execute(sql, employee.getName(), employee.getAddress(), employee.getSalary(), employee.getRole(),empId);
    }

    public static boolean delete (OwnerEmployee employee, String empId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employee WHERE employeeID=?";
        return CrudUtil.execute(sql,employee.getEmpId());
    }

}
