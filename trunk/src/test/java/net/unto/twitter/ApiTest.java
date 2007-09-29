package net.unto.twitter;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import org.easymock.EasyMock;
import org.junit.Test;

public class ApiTest {

  @Test
  public void testGetPublicTimeline() throws TwitterException, IOException {
    Api api = new Api();
    TwitterHttpManager mockTwitterHttpManager = getMockTwitterHttpManager();
    String json = readTestData("public-timeline.json");
    String url = "http://twitter.com/statuses/public_timeline.json";
    Parameter[] parameters = new Parameter[] {new Parameter("since_id", null)};
    EasyMock.expect(mockTwitterHttpManager.get(EasyMock.eq(url), EasyMock.aryEq(parameters))).andReturn(json);
    EasyMock.replay(mockTwitterHttpManager);
    api.setTwitterHttpManager(mockTwitterHttpManager);
    Status[] statuses = api.getPublicTimeline();
    assert(10 == statuses.length);
    EasyMock.verify(mockTwitterHttpManager);
  }

  @Test
  public void testGetUserTimelineLong() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetUserTimelineLongInt() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetFriendsTimeline() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetFriends() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetFollowers() {
    fail("Not yet implemented");
  }

  @Test
  public void testPostUpdate() {
    fail("Not yet implemented");
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
    return EasyMock.createMock(TwitterHttpManager.class);
  }
}
