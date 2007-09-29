package net.unto.twitter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import org.junit.Test;

public class JsonUtilTest {

  private String TEST_DATA_DIR = "src/test/data/";
  
  private int MAX_TEST_DATA_FILE_SIZE = 16384;
  
  @Test
  public void testNewStatusArrayString() {
    // Status[] statuses = JsonUtil.newStatusArray(jsonString);
  }

  @Test
  public void testNewStatusArrayJSONArray() {
    // Status[] statuses = JsonUtil.newStatusArray(jsonArray);
  }

  @Test
  public void testNewStatusString() throws TwitterException, IOException {
    Status status = JsonUtil.newStatus(readTestData("status-271680732.json"));
    assertEquals(271680732, (long)status.getId());
  }

  @Test
  public void testNewStatusJSONObject() {
    fail("Not yet implemented");
  }

  @Test
  public void testNewUserArrayString() {
    fail("Not yet implemented");
  }

  @Test
  public void testNewUserArrayJSONArray() {
    fail("Not yet implemented");
  }

  @Test
  public void testNewUserString() {
    fail("Not yet implemented");
  }

  @Test
  public void testNewUserJSONObject() {
    fail("Not yet implemented");
  }
  
  private String readTestData(String fileName) throws IOException {
    return readFromFile(new File(TEST_DATA_DIR, fileName));
  }
  
  private String readFromFile(File file) throws IOException {
    Reader reader = new FileReader(file);
    CharBuffer charBuffer = CharBuffer.allocate(MAX_TEST_DATA_FILE_SIZE);
    reader.read(charBuffer);
    charBuffer.position(0);
    return charBuffer.toString();
  }

}
