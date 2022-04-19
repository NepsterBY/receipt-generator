package ru.clevertec.test.receipt.model;

import java.util.Date;
import java.util.List;

public class Receipt {

    private List<ItemTotal> itemTotals;
    private double finalTotal, finalDiscount;

    public Receipt(List<ItemTotal> itemTotals) {
        this.itemTotals = itemTotals;
        this.finalDiscount = 0;
        this.finalTotal = 0;
    }

    public List<ItemTotal> getItemTotals() {
        return itemTotals;
    }

    public void setItemTotals(List<ItemTotal> itemTotals) {
        this.itemTotals = itemTotals;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public double getFinalDiscount() {
        return finalDiscount;
    }

    public void setFinalDiscount(double finalDiscount) {
        this.finalDiscount = finalDiscount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        sb.append("           ЧЕК ПРОДАЖИ           \n")
          .append("              №1234              \n")
          .append("           Магазин №10           \n")
          .append("     г.Гомель, ул.Советская,1    \n")
          .append("   ").append(date).append("\n")
          .append("=================================\n")
          .append("Количество Наименование Стоимость\n")
          .append("---------------------------------\n");

        for (ItemTotal itemTotal : itemTotals) {
            sb.append(itemTotal).append("\n");
            finalTotal += itemTotal.getTotal();
            finalDiscount += itemTotal.getDiscount();
        }

        sb.append("---------------------------------\n")
          .append("Итого без скидки: ").append(finalTotal + finalDiscount).append("\n")
          .append("Сумма скидки: ").append(finalDiscount).append("\n")
          .append("Итого со скидкой: ").append(finalTotal).append("\n")
          .append("=================================\n")
          .append("      Cпасибо за покупку!        \n");
        return sb.toString();
    }
}
