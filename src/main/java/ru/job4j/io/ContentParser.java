package ru.job4j.io;

import java.io.IOException;

public interface ContentParser {

    String getContentWithoutUnicode() throws IOException;

    String getContent() throws IOException;

}
