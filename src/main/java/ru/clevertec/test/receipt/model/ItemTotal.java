package ru.clevertec.test.receipt.model;

public class ItemTotal {
    private Item item;
    private int qty;
    private double total;
    private double discount;

    public ItemTotal(Item item, int qty, double total, double discount) {
        this.item = item;
        this.qty = qty;
        this.total = total;
        this.discount = discount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-10s %10.2f %s", qty, item.getName(), total,
            (discount > 0 ? String.format("\n%19s Cкидка %5.2f", " ", discount) : ""));
    }
}
