package ru.job4j.io;

import java.io.IOException;

public interface ContentSaver {
    void saveContent(String content) throws IOException;
}
