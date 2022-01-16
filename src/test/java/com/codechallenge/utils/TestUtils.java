package com.codechallenge.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TestUtils {

  private static final String TEST_LOG_FILE_NAME = "./test.log";

  public static List<String> readStringsFromLogFile() throws IOException {
    return Files.readAllLines(Path.of(TEST_LOG_FILE_NAME));
  }

  public static void removeLogFile() throws IOException {
    Files.delete(Path.of(TEST_LOG_FILE_NAME));
  }

  public static void clearLogFile() throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(TEST_LOG_FILE_NAME);
    writer.print("");
    writer.close();
  }

}
