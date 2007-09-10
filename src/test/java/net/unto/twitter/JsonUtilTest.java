package net.unto.twitter;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonUtilTest {

  private static String STATUS_JSON_STRING = "{\"created_at\": \"Fri Jan 26 23:17:14 +0000 2007\","
      + " \"id\": 4391023, \"relative_created_at\": \"about 10 minutes ago\", "
      + "\"text\": \"Canvas. JC Penny. Three ninety-eight.\", \"user\": "
      + "{\"description\": \"Canvas. JC Penny. Three ninety-eight.\", \"id\": "
      + "718443, \"location\": \"Okinawa, Japan\", \"name\": \"Kesuke Miyagi\","
      + " \"profile_image_url\": \"http:\\/\\/twitter.com\\/system\\/user\\/pro"
      + "file_image\\/718443\\/normal\\/kesuke.png\", \"screen_name\": "
      + "\"kesuke\", \"url\": \"http:\\/\\/twitter.com\\/kesuke\"}}";

  @Test
  public void testNewStatusArrayString() throws TwitterException {

    // Status[] statuses = JsonUtil.newStatusArray(jsonString);
  }

  @Test
  public void testNewStatusArrayJSONArray() {
    // Status[] statuses = JsonUtil.newStatusArray(jsonArray);
  }

  @Test
  public void testNewStatusString() throws TwitterException {
    Status status = JsonUtil.newStatus(STATUS_JSON_STRING);
    assertEquals(4391023L, status.getId());
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

}
