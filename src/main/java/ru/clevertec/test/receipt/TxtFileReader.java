package ru.clevertec.test.receipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtFileReader {
    public List<String> read(String filePath) {
        List<String> arrayList = new ArrayList<>();
        try (Scanner s = new Scanner(new File(filePath))) {
            while (s.hasNextLine()) {
                arrayList.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}