package lk.ijse.scaleShop.to;

public class Supplier {
    private String id;
    private String name;
    private int number;
    private String type;
    private int qty;

    public Supplier() {
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Supplier(String id, String name, int number, String type, int qty) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.type = type;
        this.qty = qty;
    }
}
