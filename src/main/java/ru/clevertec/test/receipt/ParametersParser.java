package ru.clevertec.test.receipt;

import java.util.HashMap;
import java.util.Map;

public class ParametersParser {

    private final String[] parameters;

    public ParametersParser(String[] parameters) {
        this.parameters = parameters;
    }

    public String getCardNumber() {
        for (String param : parameters) {
            if (param.startsWith("card-")) {
                return param.substring(5);
            }
        }

        return null;
    }

    public Map<Long, Integer> getItemsQuantity() {
        Map<Long, Integer> itemsNumber = new HashMap<>();

        for (String param : parameters) {
            String[] splitParam = param.split("-");
            if (splitParam.length != 2 || "card".equals(splitParam[0])) {
                continue;
            }
            itemsNumber.put(Long.parseLong(splitParam[0]), Integer.parseInt(splitParam[1]));
        }

        return itemsNumber;
    }
}
