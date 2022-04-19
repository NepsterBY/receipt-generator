package ru.clevertec.test.receipt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TxtFileWriter {
    public void writeLines(List<String>  lines, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (String line:lines) {
                fileWriter.write(line+ "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeObject(Object object, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}