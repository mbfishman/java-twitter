package net.unto.twitter;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ApiTest {

  @Test
  public void testGetPublicTimeline() throws TwitterException {
//    HttpConnectionManager mock = createNiceMock(HttpConnectionManager.class);
//    replay(mock);
    Api api = new Api();
//    api.setHttpConnectionManager(mock);
    Status[] statuses = api.getPublicTimeline();
    for (int i = 0; i < statuses.length; i++) {
      System.out.println(statuses[i]);
    }
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

}
