package lk.ijse.scaleShop.view.tm;

public class OrderDetailsTm {
    private String id;
    private String name;
    private String type;
    private double price;
    private int qty;

    public OrderDetailsTm() {
    }

    public OrderDetailsTm(String id, String name, String type, double price, int qty) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
