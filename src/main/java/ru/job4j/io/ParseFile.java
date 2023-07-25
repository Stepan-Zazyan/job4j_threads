package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile implements ContentParser {
    private final File file;

    public ParseFile(File file) {
            this.file = file;
    }

    @Override
    public String getContent() {
        return content(s -> true);
    }

    @Override
    public String getContentWithoutUnicode() {
        return content(s -> (int) s < 0x80);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
}
