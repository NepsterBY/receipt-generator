package ru.clevertec.test.receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import ru.clevertec.test.receipt.model.Item;
import ru.clevertec.test.receipt.model.ItemTotal;

public class Main {

    private static final int MIN_QTY_FOR_DISCOUNT = 5;
    private static final double DISCOUNT_COEFFICIENT = 0.9;

    public static void main(String[] args) {
        System.out.println("ЧЕК ПРОДАЖИ №12345");
        System.out.println("Магазин №10");
        System.out.println("г.Гомель, ул.Советская,1");

        Date date = new Date();
        System.out.println(date);
        System.out.println();

        List<Item> items = List.of(
            new Item(1, "Апельсин", 5.5, true),
            new Item(2, "Молоко", 2.2, false),
            new Item(3, "Чипсы", 1.5, true),
            new Item(4, "Вода", 0.5, true));
        List<Integer> cards = List.of(1234, 2345, 4321);

        List<ItemTotal> itemTotals = new ArrayList<>();
        ParametersParser parametersParser = new ParametersParser(args);

        for (Entry<Long, Integer> itemQtyEntry: parametersParser.getItemsQuantity().entrySet()) {
            Item item = findItemById(items, itemQtyEntry.getKey());
            Integer qty = itemQtyEntry.getValue();

            ItemTotal itemTotal = getItemTotal(cards, parametersParser, item, qty);
            itemTotals.add(itemTotal);
        }

        printReceipt(itemTotals);
    }

    private static void printReceipt(List<ItemTotal> itemTotals) {
        double finalTotal = 0, finalDiscount = 0;
        for (ItemTotal itemTotal : itemTotals) {
            System.out.println(itemTotal);
            finalTotal += itemTotal.getTotal();
            finalDiscount += itemTotal.getDiscount();
        }
        System.out.printf("Итого без скидки: %.2f%n", finalTotal + finalDiscount);
        System.out.printf("Сумма скидки: %.2f%n", finalDiscount);
        System.out.printf("Итого со скидкой: %.2f%n", finalTotal);
    }

    private static ItemTotal getItemTotal(List<Integer> cards, ParametersParser parametersParser, Item item,
        Integer qty) {
        Integer cardNumber = parametersParser.getCardNumber();
        boolean cardPassed = false;
        if (cardNumber != null) {
            cardPassed = cards.contains(cardNumber);
        }

        double total = item.getPrice() * qty;
        double totalWithDiscount = total;
        if (qty > MIN_QTY_FOR_DISCOUNT && cardPassed && item.isOnPromotion()) {
            totalWithDiscount *= DISCOUNT_COEFFICIENT;
        }
        return new ItemTotal(item, qty, totalWithDiscount, total - totalWithDiscount);
    }

    private static Item findItemById(List<Item> items, Long id) {
        for (Item item: items) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new RuntimeException("Неверный id товара " + id);
    }

}

