package net.unto.twitter;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import net.unto.twitter.TwitterProtos.Device;
import net.unto.twitter.TwitterProtos.Geocode;
import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.UtilProtos.Url;
import net.unto.twitter.UtilProtos.Url.Parameter;
import net.unto.twitter.methods.Request;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * Unit tests for the ApiTest class.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
public class ApiTest {

  @Test
  public void testCreateBlockRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/blocks/create/dewitt.json",
                 api.createBlock("dewitt").build().toString());
  }

  @Test
  public void testCreateFavoriteRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/favorites/create/12345.json",
                 api.createFavorite(12345).build().toString());
  }
  
  @Test
  public void testCreateFriendshipRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/friendships/create/dewitt.json",
                 api.createFriendship("dewitt").build().toString());
  }
  
  @Test
  public void testDestroyBlockRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/blocks/destroy/dewitt.json",
                 api.destroyBlock("dewitt").build().toString());
  }
  
  @Test
  public void testDestroyDirectMessageRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/direct_messages/destroy/12345.json",
                 api.destroyDirectMessage(12345).build().toString());
  }
 
  @Test
  public void testDestroyFavoriteRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/favorites/destroy/12345.json",
                 api.destroyFavorite(12345).build().toString());
  }
  
  @Test
  public void testDestroyFriendshipRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/friendships/destroy/dewitt.json",
                 api.destroyFriendship("dewitt").build().toString());
  }
  
  @Test
  public void testDestroyStatusRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/statuses/destroy/12345.json",
                 api.destroyStatus(12345).build().toString());
  }
  
  @Test
  public void testEndSessionRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    assertEquals("http://twitter.com:80/account/end_session.json",
                 api.endSession().build().toString());
  }
  
  @Test
  public void testFavoritesRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.favorites().id("dewitt").page(1).build();
    assertEquals("http://twitter.com:80/favorites/dewitt.json", request.toString());
    assertHasParameter(request.toUrl(), "page", "1");
  }
  
  @Test
  public void testFollowerIdsRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.followerIds().id("dewitt").build();
    assertEquals("http://twitter.com:80/followers/ids/dewitt.json", request.toString());
  } 

  @Test
  public void testFollowersRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.followers().id("dewitt").page(1).build();
    assertEquals("http://twitter.com:80/statuses/followers/dewitt.json", request.toString());
    assertHasParameter(request.toUrl(), "page", "1");
  } 
  
  @Test
  public void testFollowRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.follow("dewitt").build();
    assertEquals("http://twitter.com:80/notifications/follow/dewitt.json", request.toString());
  } 

  @Test
  public void testFriendIdsRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.friendIds().id("dewitt").build();
    assertEquals("http://twitter.com:80/friends/ids/dewitt.json", request.toString());
  }
  
  @Test
  public void testFriendshipExistsRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.friendshipExists("ev", "biz").build();
    assertEquals("http://twitter.com:80/friendships/exists.json", request.toString());
    assertHasParameter(request.toUrl(), "user_a", "ev");
    assertHasParameter(request.toUrl(), "user_b", "biz");
  }
  
  @Test
  public void testFriendsRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.friends().id("dewitt").page(1).build();
    assertEquals("http://twitter.com:80/statuses/friends/dewitt.json", request.toString());
    assertHasParameter(request.toUrl(), "page", "1");
  }
  
  @Test
  public void testFriendsTimelineRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    DateTime epoch = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    Request request = api.friendsTimeline().count(10).page(1).sinceId(12345).since(epoch).build();
    assertEquals("http://twitter.com:80/statuses/friends_timeline.json", request.toString());
    assertHasParameter(request.toUrl(), "count", "10");
    assertHasParameter(request.toUrl(), "page", "1");
    assertHasParameter(request.toUrl(), "since_id", "12345");
    assertHasParameter(request.toUrl(), "since", "Thu Jan 01 00:00:00 +0000 1970");    
  }
  
  @Test
  public void testLeaveRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.leave("dewitt").build();
    assertEquals("http://twitter.com:80/notifications/leave/dewitt.json", request.toString());
  } 
  
  @Test
  public void testNewDirectMessageRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.newDirectMessage("dewitt", "Hello, DeWitt").build();
    assertEquals("http://twitter.com:80/direct_messages/new.json", request.toString());
    assertHasParameter(request.toUrl(), "user", "dewitt");
    assertHasParameter(request.toUrl(), "text", "Hello, DeWitt"); 
  }
  
  @Test
  public void testPublicTimelineRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.publicTimeline().build();
    assertEquals("http://twitter.com:80/statuses/public_timeline.json", request.toString()); 
  }
  
  @Test
  public void testRateLimitStatusRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.rateLimitStatus().build();
    assertEquals("http://twitter.com:80/account/rate_limit_status.json", request.toString()); 
  }

  @Test
  public void testRepliesRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    DateTime epoch = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    Request request = api.replies().page(1).sinceId(12345).since(epoch).build();
    assertEquals("http://twitter.com:80/statuses/replies.json", request.toString());
    assertHasParameter(request.toUrl(), "page", "1");
    assertHasParameter(request.toUrl(), "since_id", "12345");
    assertHasParameter(request.toUrl(), "since", "Thu Jan 01 00:00:00 +0000 1970");    
  }
  
  @Test
  public void testSearchRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Geocode geocode = Geocode.newBuilder().setLatitude(40.757929).setLongitude(-73.985506).setRadius(25).setUnit(Geocode.Unit.KILOMETERS).build();
    Request request = api.search("foo").geocode(geocode).page(1).resultsPerPage(10).sinceId(12345).lang("en").showUser(true).build();
    assertEquals("http://search.twitter.com:80/search.json", request.toString());
    assertHasParameter(request.toUrl(), "geocode", "40.757929,-73.985506,25km");
    assertHasParameter(request.toUrl(), "page", "1");
    assertHasParameter(request.toUrl(), "rpp", "10");
    assertHasParameter(request.toUrl(), "since_id", "12345");
    assertHasParameter(request.toUrl(), "lang", "en");
    assertHasParameter(request.toUrl(), "show_user", "true");
  }
  
  @Test
  public void testSentDirectMessagesRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    DateTime epoch = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    Request request = api.sentDirectMessages().page(1).sinceId(12345).since(epoch).build();
    assertEquals("http://twitter.com:80/direct_messages/sent.json", request.toString());
    assertHasParameter(request.toUrl(), "page", "1");
    assertHasParameter(request.toUrl(), "since_id", "12345");
    assertHasParameter(request.toUrl(), "since", "Thu Jan 01 00:00:00 +0000 1970");    
  }
  
  @Test
  public void testShowStatusRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.showStatus(12345).build();
    assertEquals("http://twitter.com:80/statuses/show/12345.json", request.toString());
  } 

  @Test
  public void testShowUserRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request requestById = api.showUser().id("dewitt").build();
    assertEquals("http://twitter.com:80/users/show/dewitt.json", requestById.toString());
    Request requestByUserId = api.showUser().userId(12345).build();
    assertEquals("http://twitter.com:80/users/show.json", requestByUserId.toString());
    assertHasParameter(requestByUserId.toUrl(), "user_id", "12345");
    Request requestByScreenName = api.showUser().screenName("dewitt").build();
    assertEquals("http://twitter.com:80/users/show.json", requestByScreenName.toString());
    assertHasParameter(requestByScreenName.toUrl(), "screen_name", "dewitt");
  }
  
  @Test
  public void testTestRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.test().build();
    assertEquals("http://twitter.com:80/help/test.json", request.toString());
  }
  
  @Test
  public void testTrendsRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.trends().build();
    assertEquals("http://search.twitter.com:80/trends.json", request.toString());
  }
  
  @Test
  public void testUpdateDeliveryDeviceRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request requestNone = api.updateDeliveryDevice(Device.NONE).build();
    assertEquals("http://twitter.com:80/account/update_delivery_device.json", requestNone.toString());
    assertHasParameter(requestNone.toUrl(), "device", "none");
    Request requestIm = api.updateDeliveryDevice(Device.IM).build();
    assertEquals("http://twitter.com:80/account/update_delivery_device.json", requestIm.toString());
    assertHasParameter(requestIm.toUrl(), "device", "im");
    Request requestSms = api.updateDeliveryDevice(Device.SMS).build();
    assertEquals("http://twitter.com:80/account/update_delivery_device.json", requestSms.toString());
    assertHasParameter(requestSms.toUrl(), "device", "sms");
  }
  
  @Test
  public void testUpdateProfileBackgroundImageRequestUrl() {
    fail("Not implemented");
  }
  
  @Test
  public void testUpdateProfileColorsRequestUrl() {
    fail("Not implemented");
  }
  
  @Test
  public void testUpdateProfileImageRequestUrl() {
    fail("Not implemented");
  }
  
  @Test
  public void testUpdateProfileRequestUrl() {
    fail("Not implemented");
  }
  
  @Test
  public void testUpdateStatusRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.updateStatus("Hello, Twitter!").inReplyToStatusId(12345).build();
    assertEquals("http://twitter.com:80/statuses/update.json", request.toString());
    assertHasParameter(request.toUrl(), "status", "Hello, Twitter!");
    assertHasParameter(request.toUrl(), "in_reply_to_status_id", "12345");
  }
  
  @Test
  public void testUserTimelineRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    DateTime epoch = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    Request request = api.userTimeline().id("dewitt").count(10).page(1).sinceId(12345).since(epoch).build();
    assertEquals("http://twitter.com:80/statuses/user_timeline/dewitt.json", request.toString());
    assertHasParameter(request.toUrl(), "count", "10");
    assertHasParameter(request.toUrl(), "page", "1");
    assertHasParameter(request.toUrl(), "since_id", "12345");
    assertHasParameter(request.toUrl(), "since", "Thu Jan 01 00:00:00 +0000 1970");    
  }
  
  
  @Test
  public void testVerifyCredentialsRequestUrl() {
    Api api = Api.builder().username("test").password("test").build();
    Request request = api.verifyCredentials().build();
    assertEquals("http://twitter.com:80/account/verify_credentials.json", request.toString());
  }
  
  
  void assertHasParameter(Url url, String name, Object value) {
    Parameter expected = Parameter.newBuilder().setName(name).setValue(value.toString()).build();
    for (Parameter actual : url.getParametersList()) {
      if (actual.equals(expected)) {
        return;
      }
    }
    fail(String.format("Actual URL %s does not contain parameter %s", url, expected));
  }

  
  
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
    List<Status> statuses = api.publicTimeline().build().get();
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
    List<Status> statuses = api.friendsTimeline().build().get();
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
