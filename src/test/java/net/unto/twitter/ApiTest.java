package net.unto.twitter;

import static org.junit.Assert.*;
import static org.easymock.classextension.EasyMock.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.IOException;

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
