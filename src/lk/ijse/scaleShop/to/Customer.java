package lk.ijse.scaleShop.to;

public class Customer {
    private String custId;
    private String name;
    private String address;
    private int number;
    private String empId;

    public Customer() {
    }

    public Customer(String custId, String name, String address, int number, String empId) {
        this.custId = custId;
        this.name = name;
        this.address = address;
        this.number = number;
        this.empId = empId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId='" + custId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", number=" + number +
                ", empId='" + empId + '\'' +
                '}';
    }
}
