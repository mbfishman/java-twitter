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
import net.unto.twitter.UtilProtos.Url;

import org.junit.Test;

/**
 * Unit tests for the ApiTest class.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
public class ApiTest {

  @Test
  public void testGetPublicTimeline() throws IOException {
    HttpManager mockTwitterHttpManager = getMockTwitterHttpManager();
    Api api = Api.builder().httpManager(mockTwitterHttpManager).build();
    String json = readTestData("public-timeline.json");
    Url url = Url.newBuilder()
        .setScheme(Url.Scheme.HTTP)
        .setHost("twitter.com")
        .setPort(80)
        .setPath("/statuses/public_timeline.json")
        .build();
    expect(mockTwitterHttpManager.get(url)).andReturn(json);
    replay(mockTwitterHttpManager);
    List<Status> statuses = api.PublicTimeline().build().get();
    assertTrue(20 == statuses.size());
    assertTrue(301231062L == statuses.get(0).getId());
    assertEquals("I should sleep or else...", statuses.get(0).getText());
    assertTrue(3188291L == statuses.get(0).getUser().getId());
    verify(mockTwitterHttpManager);
  }
  
  @Test
  public void testGetFriendsTimeline() throws IOException {
    HttpManager mockTwitterHttpManager = getMockTwitterHttpManager();
    Api api = Api.builder()
        .httpManager(mockTwitterHttpManager)
        .build();
    String json = readTestData("friends-timeline-javaclient.json");
    Url url = Url.newBuilder()
        .setScheme(Url.Scheme.HTTP)
        .setHost("twitter.com")
        .setPort(80)
        .setPath("/statuses/friends_timeline.json")
        .build();
    expect(mockTwitterHttpManager.get(url)).andReturn(json);
    expect(mockTwitterHttpManager.hasCredentials()).andReturn(true);
    replay(mockTwitterHttpManager);
    List<Status> statuses = api.FriendsTimeline().build().get();
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
  
  private HttpManager getMockTwitterHttpManager() {
    return createMock(HttpManager.class);
  }
}
