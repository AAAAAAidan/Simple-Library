package org.simplelibrary.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import java.io.IOException;

class UrlReaderTests {

  @Test
  @Order(1)
  void testGetByteArrayFromUrl() throws IOException {
    String url = "https://www.gutenberg.org/cache/epub/46/pg46.cover.small.jpg";
    byte[] bytes = UrlReader.getByteArrayFromUrl(url);
    assertNotNull(bytes);
  }

  @Test
  @Order(2)
  void testGetJsonObjectFromUrl() throws IOException {
    String url = "https://gutendex.com/books/?page=39";
    JsonObject json = UrlReader.getJsonObjectFromUrl(url);
    assertNotNull(json);
  }

}
