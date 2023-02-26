package lk.ijse.scaleShop.to;

public class OwnerEmployee {
    private String empId;
    private String name;
    private String address;
    private double salary;
    private  String role;

    public OwnerEmployee() {
    }

    public OwnerEmployee(String empId, String name, String address, double salary, String role) {
        this.empId = empId;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.role = role;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "OwnerEmployee{" +
                "empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", role='" + role + '\'' +
                '}';
    }
}
