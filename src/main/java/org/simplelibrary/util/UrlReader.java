package org.simplelibrary.util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import java.io.*;
import java.net.URL;

/**
 * Utility class for retrieving data from URLs.
 */
public final class UrlReader {

  /**
   * Gets a byte array from a URL fetch.
   *
   * @param urlText the URL to fetch
   * @return the response as a byte array
   * @throws IOException if the input/output fails to read/write
   */
  public static byte[] getByteArrayFromUrl(String urlText) throws IOException {
    URL url = new URL(urlText);
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    InputStream input = url.openStream();
    int n = 0;
    byte[] buffer = new byte[4096];

    while ((n = input.read(buffer)) != -1) {
      output.write(buffer, 0, n);
    }

    input.close();
    return output.toByteArray();
  }

  /**
   * Gets a JSON object from a URL fetch.
   *
   * @param urlText the URL to fetch
   * @return the response as a JSON object
   * @throws IOException if the input/output fails to read/write
   */
  public static JsonObject getJsonObjectFromUrl(String urlText) throws IOException {
    URL url = new URL(urlText);
    InputStream input = url.openStream();
    JsonParser parser = Json.createParser(input);
    JsonObject object = null;

    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();

      if (event == JsonParser.Event.START_OBJECT) {
        object = parser.getObject();
      }
    }

    input.close();
    return object;
  }

}
