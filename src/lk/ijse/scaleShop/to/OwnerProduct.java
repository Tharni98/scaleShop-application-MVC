package lk.ijse.scaleShop.to;

public class OwnerProduct {
    private String code;
    private String type;
    private String description;
    private double price;
    private int qty;

    public OwnerProduct() {
    }

    public OwnerProduct(String code, String type, String description, double price, int qty) {
        this.code = code;
        this.type = type;
        this.description = description;
        this.price = price;
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
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OwnerProduct{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
