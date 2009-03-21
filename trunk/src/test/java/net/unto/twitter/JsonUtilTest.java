package net.unto.twitter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import net.unto.twitter.TwitterProtos.DirectMessage;
import net.unto.twitter.TwitterProtos.RateLimitStatus;
import net.unto.twitter.TwitterProtos.Results;
import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.TwitterProtos.Trends;
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
    assertEquals("\u263A", status.getText());
    assertEquals("dewitt", status.getUser().getScreenName());
  }

  @Test
  public void testNewStatusList() throws IOException {
    String json = readTestData("status_list.json");
    List<Status> statuses = JsonUtil.newStatusList(json);
    assertEquals(new Integer(20), new Integer(statuses.size()));
  }

  @Test
  public void testNewDirectMessageList() throws IOException {
    String json = readTestData("direct_messages.json");
    List<DirectMessage> directMessages = JsonUtil.newDirectMessageList(json);
    assertEquals(new Integer(2), new Integer(directMessages.size()));
  }


  @Test
  public void testNewDirectMessage() throws IOException {
    String json = readTestData("direct_message.json");
    DirectMessage directMessage = JsonUtil.newDirectMessage(json);
    assertEquals("dewitt", directMessage.getSender().getScreenName());
  }

  @Test
  public void testNewLongArray() throws IOException {
    String json = readTestData("ids.json");
    long[] ids = JsonUtil.newLongArray(json);
    assertEquals(new Integer(109), new Integer(ids.length));
  }


  @Test
  public void testNewTrends() throws IOException {
    String json = readTestData("trends.json");
    Trends trends = JsonUtil.newTrends(json);
    assertEquals(new Integer(10), new Integer(trends.getTrendsList().size()));
  }


  @Test
  public void testNewResults() throws IOException {
    String json = readTestData("search.json");
    Results results = JsonUtil.newResults(json);
    assertEquals(new Integer(15), new Integer(results.getResultsList().size()));
  }

  @Test
  public void testNewRateLimitStatus() throws IOException {
    String json = readTestData("rate_limit_status.json");
    RateLimitStatus rateLimitStatus = JsonUtil.newRateLimitStatus(json);
    assertEquals(new Integer(100), new Integer(rateLimitStatus.getHourlyLimit()));
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
