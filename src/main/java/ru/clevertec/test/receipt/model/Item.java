package ru.clevertec.test.receipt.model;

public class Item {
    private long id;
    private String name;
    private double price;
    private boolean isOnPromotion;

    public Item(long id, String name, double price, boolean isOnPromotion) {
        this.id = id;
        this.isOnPromotion = isOnPromotion;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOnPromotion() {
        return isOnPromotion;
    }

    public void setOnPromotion(boolean onPromotion) {
        isOnPromotion = onPromotion;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", isOnPromotion=" + isOnPromotion +
            '}';
    }
}
