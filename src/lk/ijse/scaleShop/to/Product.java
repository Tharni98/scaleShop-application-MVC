package lk.ijse.scaleShop.to;

public class Product {
    private String code;
    private String type;
    private String description;
    private double unitPrice;
    private int qty;

    public Product(String custId, String name, String address, int number, String empId){

    }

    public Product(String code, String description, double unitPrice, int qty) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public Product(String code, String type, String description, double UnitPrice, int qty) {
        this.code = code;
        this.type = type;
        this.description = description;
        this.unitPrice = UnitPrice;
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return unitPrice;
    }

    public void setPrice(double price) {
        this.unitPrice = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
