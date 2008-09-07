package net.unto.twitter;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import net.unto.twitter.TwitterProtos.Status;

import org.junit.Test;

/**
 * Unit tests for the ApiTest class.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
public class ApiTest {

  @Test
  public void testGetPublicTimeline() throws TwitterException, IOException {
    Api api = new Api();
    TwitterHttpManager mockTwitterHttpManager = getMockTwitterHttpManager();
    String json = readTestData("public-timeline.json");
    String url = "http://twitter.com/statuses/public_timeline.json";
    Parameter[] parameters = new Parameter[] {new Parameter("since_id", null)};
    expect(mockTwitterHttpManager.get(eq(url), aryEq(parameters))).andReturn(json);
    replay(mockTwitterHttpManager);
    api.setTwitterHttpManager(mockTwitterHttpManager);
    List<Status> statuses = api.getPublicTimeline();
    assertTrue(20 == statuses.size());
    assertTrue(301231062L == statuses.get(0).getId());
    assertEquals("I should sleep or else...", statuses.get(0).getText());
    assertTrue(3188291L == statuses.get(0).getUser().getId());
    verify(mockTwitterHttpManager);
  }
  
  @Test
  public void testGetFriendsTimeline() throws TwitterException, IOException {
    Api api = new Api();
    api.setCredentials("javaclient", "xxyzzy");
    TwitterHttpManager mockTwitterHttpManager = getMockTwitterHttpManager();
    String json = readTestData("friends-timeline-javaclient.json");
    String url = "http://twitter.com/statuses/friends_timeline.json";
    Parameter[] parameters = new Parameter[] {new Parameter("since", null), new Parameter("page", null)};
    expect(mockTwitterHttpManager.get(eq(url), aryEq(parameters))).andReturn(json);
    expect(mockTwitterHttpManager.hasCredentials()).andReturn(true);
    replay(mockTwitterHttpManager);
    api.setTwitterHttpManager(mockTwitterHttpManager);
    List<Status> statuses = api.getFriendsTimeline();
    assertTrue(1 == statuses.size());
    assertTrue(303230492L == statuses.get(0).getId());
    assertTrue(673483L == statuses.get(0).getUser().getId());
    verify(mockTwitterHttpManager);
  }
  
  @Test
  public void testGetFriendsTimelineString() throws TwitterException, IOException {
    Api api = new Api();
    TwitterHttpManager mockTwitterHttpManager = getMockTwitterHttpManager();
    String json = readTestData("friends-timeline-javaclient.json");
    String url = "http://twitter.com/statuses/friends_timeline/javaclient.json";
    Parameter[] parameters = new Parameter[] {new Parameter("since", null), new Parameter("page", null)};
    expect(mockTwitterHttpManager.get(eq(url), aryEq(parameters))).andReturn(json);
    replay(mockTwitterHttpManager);
    api.setTwitterHttpManager(mockTwitterHttpManager);
    List<Status> statuses = api.getFriendsTimeline("javaclient");
    assertTrue(1 == statuses.size());
    assertTrue(303230492L == statuses.get(0).getId());
    assertTrue(673483L == statuses.get(0).getUser().getId());
    verify(mockTwitterHttpManager);
  }
  
  private String TEST_DATA_DIR = "src/test/data/";
  
  private int MAX_TEST_DATA_FILE_SIZE = 16384;
  
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
  
  private TwitterHttpManager getMockTwitterHttpManager() {
    return createMock(TwitterHttpManager.class);
  }
}
