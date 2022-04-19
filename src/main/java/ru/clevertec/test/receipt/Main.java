package ru.clevertec.test.receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import ru.clevertec.test.receipt.model.Item;
import ru.clevertec.test.receipt.model.ItemTotal;
import ru.clevertec.test.receipt.model.Receipt;

public class Main {

    private static final int MIN_QTY_FOR_DISCOUNT = 5;
    private static final double DISCOUNT_COEFFICIENT = 0.9;

    public static void main(String[] args) {

        TxtFileReader reader = new TxtFileReader();
        TxtFileWriter writer = new TxtFileWriter();

        String cardRegex = "^([0-9]{4})$";
        List<String> cards = reader.read("src/main/resources/cards.txt");
        List<String> validCards = new ArrayList<>();
        List<String> invalidCards = new ArrayList<>();
        for(String card: cards) {
            if (Pattern.matches(cardRegex, card)) {
                validCards.add(card);
            } else {
                invalidCards.add(card);
            }
        }
        writer.writeLines(validCards, "\\Программирование\\Java\\receipt-generator\\validCards.txt");
        writer.writeLines(invalidCards, "\\Программирование\\Java\\receipt-generator\\invalidCards.txt");

        String productsRegex = "^([0-9]+)\\s+(([А-ЯЁ][а-яё]{2,29})|([A-Z][a-z]{2,29}))\\s+(([1-9][0-9]?|100)\\.[0-9]{2})\\s+([a-z]{4,5})$";
        List<String> products = reader.read("src/main/resources/products.txt");
        List<String> validProducts = new ArrayList<>();
        List<String> invalidProducts = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        for(String product: products) {
            if (Pattern.matches(productsRegex, product)) {
                validProducts.add(product);
                String[] fieldsOfProduct = product.split(" ");
                Item item = new Item(Long.parseLong(fieldsOfProduct[0]), fieldsOfProduct[1], Double.parseDouble(fieldsOfProduct[2]), Boolean.parseBoolean(fieldsOfProduct[3]));
                items.add(item);
            } else {
                invalidProducts.add(product);
            }
        }
        writer.writeLines(validProducts, "\\Программирование\\Java\\receipt-generator\\validProducts.txt");
        writer.writeLines(invalidProducts, "\\Программирование\\Java\\receipt-generator\\invalidProducts.txt");

        List<ItemTotal> itemTotals = new ArrayList<>();
        ParametersParser parametersParser = new ParametersParser(args);

        for (Entry<Long, Integer> itemQtyEntry: parametersParser.getItemsQuantity().entrySet()) {
            Item item = findItemById(items, itemQtyEntry.getKey());
            Integer qty = itemQtyEntry.getValue();

            ItemTotal itemTotal = getItemTotal(validCards, parametersParser, item, qty);
            itemTotals.add(itemTotal);
        }
        printReceipt(itemTotals, writer);
    }

    private static void printReceipt(List<ItemTotal> itemTotals, TxtFileWriter fileWriter) {
        Receipt receipt = new Receipt(itemTotals);
        fileWriter.writeObject(receipt, "src/main/resources/check.txt");

        System.out.println("           ЧЕК ПРОДАЖИ           ");
        System.out.println("              №1234              ");
        System.out.println("           Магазин №10           ");
        System.out.println("     г.Гомель, ул.Советская,1    ");

        Date date = new Date();
        System.out.println("   "+ date);

        System.out.println("=================================");
        System.out.printf("%-1s %1s %2s\n","Количество",  "Наименование", "Стоимость");
        System.out.println("---------------------------------");
        double finalTotal = 0, finalDiscount = 0;
        for (ItemTotal itemTotal : itemTotals) {
            System.out.println(itemTotal);
            finalTotal += itemTotal.getTotal();
            finalDiscount += itemTotal.getDiscount();
        }
        System.out.println("---------------------------------");
        System.out.printf("Итого без скидки: %.2f%n", finalTotal + finalDiscount);
        System.out.printf("Сумма скидки: %.2f%n", finalDiscount);
        System.out.printf("Итого со скидкой: %.2f%n", finalTotal);
        System.out.println("=================================");
        System.out.println("      Cпасибо за покупку!        ");
    }

    private static ItemTotal getItemTotal(List<String> cards, ParametersParser parametersParser, Item item,
                                          Integer qty) {
        String cardNumber = parametersParser.getCardNumber();
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

