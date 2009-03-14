package net.unto.twitter;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.TwitterProtos.User;

import org.junit.Test;

/**
 * Unit tests for the JsonUtil class.
 */
public class JsonUtilTest {

  @Test
  public void testNewUserList() throws IOException {
    String json = readTestData("user_list.json");
    List<User> users = JsonUtil.newUserList(json);
    assertEquals(new Integer(100), new Integer(users.size()));
  }

  @Test
  public void testNewUser() throws IOException {
    String json = readTestData("user.json");
    User user = JsonUtil.newUser(json);
    assertEquals("dewitt", user.getScreenName());
  }


  @Test
  public void testNewStatus() throws IOException {
    String json = readTestData("status.json");
    Status status = JsonUtil.newStatus(json);
    assertEquals("☺", status.getText());
    assertEquals("dewitt", status.getUser().getScreenName());
  }
  
  @Test
  public void testNewStatusList() throws IOException {
    String json = readTestData("status_list.json");
    List<Status> statuses = JsonUtil.newStatusList(json);
    assertEquals(new Integer(100), new Integer(statuses.size()));
  }
  
  @Test
  public void testNewDirectMessageList() {
    fail("Not implemented");
  }


  @Test
  public void testNewDirectMessage()  {
    fail("Not implemented");
  }

  @Test
  public void testNewLongArray() {
    fail("Not implemented");
  }
  

  @Test
  public void testNewTrends() {
    fail("Not implemented");
  }


  @Test
  public void testNewResults() {
    fail("Not implemented");
  }

  @Test
  public void testNewRateLimitStatus() {
    fail("Not implemented");
  }
  
  private String TEST_DATA_DIR = "src/test/data/";

  private int MAX_TEST_DATA_FILE_SIZE = 65536;

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
