package net.unto.twitter;

import java.util.Date;
import java.util.Map;

public class Api
{
  private String username;
  private String password;
  private Map<String, String> requestHeaders;

  public Api(String username, String password, Map<String, String> requestHeaders) {
    this.username = username;
    this.password = password;
    this.requestHeaders = requestHeaders;
  }
  
  public Status[] getFriendsTimeline(String user, Date since) {
    return null;
  }
  
  public Status[] getUserTimeline(String user, Date since, int count) {
    return null;
    
  }
}
