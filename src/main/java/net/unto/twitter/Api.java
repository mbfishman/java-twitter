package net.unto.twitter;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class Api {

  public Api() {
  }

  public Status[] getPublicTimeline() throws TwitterException {
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    HttpMethod method = new GetMethod(
        "http://twitter.com/statuses/public_timeline.json");
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newStatusArray(jsonString);
  }

  public Status[] getUserTimeline(long id) throws TwitterException {
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    String url = "http://twitter.com/t/status/user_timeline/"
        + Long.toString(id);
    HttpMethod method = new GetMethod(url);
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newStatusArray(jsonString);
  }

  public Status[] getUserTimeline(long id, int count) throws TwitterException {
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    String url = "http://twitter.com/t/status/user_timeline/"
        + Long.toString(id);
    HttpMethod method = new GetMethod(url);
    NameValuePair countParameter = new NameValuePair("count", Integer
        .toString(count));
    method.setQueryString(new NameValuePair[] {countParameter});
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newStatusArray(jsonString);
  }

  public Status[] getFriendsTimeline(String username, String password)
      throws TwitterException {
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    setCredentials(httpClient, username, password);
    HttpMethod method = new GetMethod(
        "http://twitter.com/statuses/friends_timeline.json");
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newStatusArray(jsonString);
  }

  public User[] getFriends(String username, String password)
      throws TwitterException {
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    setCredentials(httpClient, username, password);
    HttpMethod method = new GetMethod(
        "http://twitter.com/statuses/friends.json");
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newUserArray(jsonString);
  }

  public User[] getFollowers(String username, String password)
      throws TwitterException {
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    setCredentials(httpClient, username, password);
    HttpMethod method = new GetMethod(
        "http://twitter.com/statuses/followers.json");
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newUserArray(jsonString);
  }

  public Status postUpdate(String username, String password, String text)
      throws TwitterException {
    if (username == null) {
      throw new IllegalArgumentException("Username must not be null");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password must not be null");
    }
    if (text == null) {
      throw new IllegalArgumentException("Status text must not be null");
    }
    HttpClient httpClient = new HttpClient();
    HttpMethod method = new PostMethod(
        "http://twitter.com/statuses/update.json");
    NameValuePair textParameter = new NameValuePair("status", text);
    method.setQueryString(new NameValuePair[] {textParameter});
    String jsonString = executeHttpMethod(httpClient, method);
    return JsonUtil.newStatus(jsonString);
  }
  
  private void setCredentials(HttpClient httpClient, String username,
      String password) {
    if (username == null) {
      throw new IllegalArgumentException("Username must not be null");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password must not be null");
    }
    Credentials credentials = new UsernamePasswordCredentials(username,
        password);
    AuthScope scope = new AuthScope("twitter.com", 80, AuthScope.ANY_REALM);
    httpClient.getState().setCredentials(scope, credentials);
  }


  private String executeHttpMethod(HttpClient client, HttpMethod method)
      throws TwitterException {
    int statusCode;
    try {
      statusCode = client.executeMethod(method);
    } catch (HttpException e) {
      throw new TwitterException(e);
    } catch (IOException e) {
      throw new TwitterException(e);
    } finally {
      method.releaseConnection();
    }
    if (statusCode != HttpStatus.SC_OK) {
      method.releaseConnection();
      throw new TwitterException("Expected 200 OK but received " + statusCode);
    }
    String responseBody;
    try {
      responseBody = method.getResponseBodyAsString();
    } catch (IOException e) {
      throw new TwitterException(e);
    } finally {
      method.releaseConnection();
    }
    return responseBody;
  }

  public static void main(String[] argv) throws TwitterException {
    Api api = new Api();
    Status[] statuses = api.getPublicTimeline();
    for (Status status : statuses) {
      System.out.println(status);
    }
  }
  
  private HttpConnectionManager manager = null;
  
  private HttpConnectionManager getHttpConnectionManager() {
    if (manager == null) {
      manager = new SimpleHttpConnectionManager();
    }
    return manager;
  }
  
  public void setHttpConnectionManager(HttpConnectionManager manager) {
    this.manager = manager;
  }
  
}
