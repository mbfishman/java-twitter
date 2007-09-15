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
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Instances of the Api class provide access to the Twitter web service.
 * 
 * @author dewitt
 */
public class Api {
  
  /**
   * Construct a new Api instance
   */
  public Api() {
  }
  
  /**
   * Returns the 20 most recent statuses from non-protected users who have set a custom user icon.  Does not require authentication.
   * 
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getPublicTimeline() throws TwitterException {
    return getPublicTimeline(null);
  }

  /**
   * Returns the 20 most recent statuses from non-protected users who have set a custom user icon.  Does not require authentication.
   * 
   * @param sinceId Optional.  Returns only public statuses with an ID greater than (that is, more recent than) the specified ID.
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getPublicTimeline(String sinceId) throws TwitterException {
    String url = "http://twitter.com/statuses/public_timeline.json";
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (sinceId != null) {
      parameters.add(new NameValuePair("since_id", sinceId));
    }
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newStatusArray(execute(method));
  }
  
  /**
   * Returns the 20 most recent statuses posted in the last 24 hours from the authenticating user and that user's friends.
   * 
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getFriendsTimeline() throws TwitterException {
    return getFriendsTimeline(null, null, null);
  }
  
  /**
   * Returns the 20 most recent statuses posted in the last 24 hours from the authenticating user and that user's friends.
   * 
   * @param id  Optional.  Specifies the ID or screen name of the user for whom to return the friends_timeline
   * @param since Optional.  Narrows the returned results to just those statuses created after the specified HTTP-formatted date.
   * @param page Optional.  Gets the 20 next most recent statuses from the authenticating user and that user's friends.
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getFriendsTimeline(String id, Date since, Integer page) throws TwitterException {
    String url;
    if (id == null) {
      url = "http://twitter.com/statuses/friends_timeline.json";
    } else {
      url = String.format("http://twitter.com/statuses/friends_timeline/%s.json", id);
    }
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (since != null) {
      // TODO(dewitt): Convert to HTTP-formatted date
      parameters.add(new NameValuePair("since", since.toString()));
    }
    if (page != null) {
      parameters.add(new NameValuePair("page", page.toString()));
    }
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newStatusArray(execute(method));
  }
  
  /**
   * Returns the 20 most recent statuses posted in the last 24 hours from the authenticating user.
   * 
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getUserTimeline() throws TwitterException {
    return getUserTimeline(null, null, null, null);
  }
  
  /**
   * Returns the 20 most recent statuses posted in the last 24 hours from the authenticating user.
   * 
   * @param id Optional.  Specifies the ID or screen name of the user for whom to return the friends_timeline. 
   * @param count Optional.  Specifies the number of statuses to retrieve.  May not be greater than 20 for performance purposes.
   * @param since Optional.  Narrows the returned results to just those statuses created after the specified HTTP-formatted date.
   * @param page  Optional. Retrieves the 20 next most recent direct messages
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getUserTimeline(String id, Integer count, Date since, Integer page) throws TwitterException {
    String url;
    if (id == null) {
      url = "http://twitter.com/statuses/user_timeline.json";
    } else {
      url = String.format("http://twitter.com/statuses/user_timeline/%s.json", id);
    }
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (since != null) {
      // TODO(dewitt): Convert to HTTP-formatted date
      parameters.add(new NameValuePair("since", since.toString()));
    }
    if (count != null) {
      parameters.add(new NameValuePair("count", count.toString()));
    }
    if (page != null) {
      parameters.add(new NameValuePair("page", page.toString()));
    }
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newStatusArray(execute(method));
  }

  /**
   * Returns a single status, specified by the id parameter below.  The status's author will be returned inline.
   * 
   * @param id  Required.  The numerical ID of the status you're trying to retrieve.
   * @return a Status instance
   * @throws TwitterException
   */
  public Status showStatus(String id) throws TwitterException {
    if (id == null) {
      throw new TwitterException("id required");
    }
    String url = String.format("http://twitter.com/statuses/show/%s.json", id);
    HttpMethod method = new GetMethod(url);
    return JsonUtil.newStatus(execute(method));
  }
  
  /**
   * Updates the authenticating user's status.
   * 
   * @param text Required.  The text of your status update.  Must not be more than 160 characters and should not be more than 140 characters to ensure optimal display.
   * @return a Status instance
   * @throws TwitterException
   */
  public Status updateStatus(String status) throws TwitterException {
    if (status == null) {
      throw new TwitterException("status required");
    }
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Returns the 20 most recent replies (status updates prefixed with @username posted by users who are friends with the user being replied to) to the authenticating user.  Replies are only available to the authenticating user; you can not request a list of replies to another user whether public or protected.
   * 
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getReplies() throws TwitterException {
    return getReplies(null);
  }
  
  /**
   * Returns the 20 most recent replies (status updates prefixed with @username posted by users who are friends with the user being replied to) to the authenticating user.  Replies are only available to the authenticating user; you can not request a list of replies to another user whether public or protected.
   * 
   * @param page Optional. Retrieves the 20 next most recent replies.
   * @return an array of Status instances
   * @throws TwitterException
   */
  public Status[] getReplies(Integer page) throws TwitterException {
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Destroys the status specified by the required ID parameter.  The authenticating user must be the author of the specified status.
   * 
   * @param id Required.  The ID of the status to destroy.
   * @return a Status instance
   * @throws TwitterException
   */
  public Status destroyStatus(String id) throws TwitterException {
    if (id == null) {
      throw new TwitterException("status required");
    }
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Returns up to 100 of the authenticating user's friends who have most recently updated, each with current status inline. 
   * 
   * @return An array of User instances
   * @throws TwitterException
   */
  public User[] getFriends() throws TwitterException {
    return getFriends(null);
  }
  
  /**
   * Returns up to 100 of the authenticating user's friends who have most recently updated, each with current status inline. 
   * 
   * @param id Optional.  The ID or screen name of the user for whom to request a list of friends
   * @return An array of User instances
   * @throws TwitterException
   */
  public User[] getFriends(String id) throws TwitterException {
    throw new TwitterException("Method not implemented");
  }

  /**
   * Returns the authenticating user's followers, each with current status inline. 
   * 
   * @return An array of User instances
   * @throws TwitterException
   */
  public User[] getFollowers() throws TwitterException {
    return getFollowers(false);
  }
  
  /**
   * Returns the authenticating user's followers, each with current status inline. 
   * 
   * @param lite Optional.  Prevents the inline inclusion of current status. 
   * @return An array of User instances
   * @throws TwitterException
   */
  public User[] getFollowers(Boolean lite) throws TwitterException {
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Returns a list of the users currently featured on the site with their current statuses inline.
   * 
   * @return An array of User instances
   * @throws TwitterException
   */
  public User[] getFeatured() throws TwitterException {
    throw new TwitterException("Method not implemented");
  }
 
  /**
   * Returns extended information of a given user, specified by ID or screen name as per the required id parameter below.
   * 
   * @param id Required.  The ID or screen name of a user. 
   * @return A User instance
   * @throws TwitterException
   */
  public User showUser(String id) throws TwitterException {
    if (id == null) {
      throw new TwitterException("id required");
    }
    throw new TwitterException("Method not implemented");
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


  private String executeHttpMethod(HttpClient client, HttpMethod method)
      throws TwitterException {
    try {
      int statusCode = client.executeMethod(method);
      if (statusCode != HttpStatus.SC_OK) {
        throw new TwitterException("Expected 200 OK. Received " + statusCode);
      }
      String responseBody = method.getResponseBodyAsString();
      if (responseBody == null) {
        throw new TwitterException("Expected response body, got null");
      }
      return responseBody;
    } catch (HttpException e) {
      throw new TwitterException(e);
    } catch (IOException e) {
      throw new TwitterException(e);
    } finally {
      method.releaseConnection();
    }
  }
  
  public void setCredentials(String username, String password) {
    if (username == null) {
      throw new IllegalArgumentException("Username must not be null");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password must not be null");
    }
    Credentials credentials = new UsernamePasswordCredentials(username, password);
    AuthScope scope = new AuthScope("twitter.com", 80, AuthScope.ANY_REALM);
    getHttpClient().getState().setCredentials(scope, credentials);
  }
  
  /**
   * Clear the stored username and password.
   */
  public void clearCredentials() {
    getHttpClient().getState( ).clearCredentials( );
  }

  private HttpConnectionManager manager = null;
  
  private HttpConnectionManager getHttpConnectionManager() {
    if (this.manager == null) {
      this.manager = new SimpleHttpConnectionManager();
    }
    return this.manager;
  }
  
  public void setHttpConnectionManager(HttpConnectionManager manager) {
    this.manager = manager;
  }
  
  private HttpClient httpClient;
  
  private HttpClient getHttpClient() {
    if (this.httpClient == null) {
      this.httpClient = new HttpClient(getHttpConnectionManager());
    }
    return this.httpClient;
  }
  
  private String execute(HttpMethod method) throws TwitterException {
    method.getParams( ).setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
    try {
      int statusCode = getHttpClient().executeMethod(method);
      if (statusCode != HttpStatus.SC_OK) {
        throw new TwitterException("Expected 200 OK. Received " + statusCode);
      }
      String responseBody = method.getResponseBodyAsString();
      if (responseBody == null) {
        throw new TwitterException("Expected response body, got null");
      }
      return responseBody;
    } catch (HttpException e) {
      throw new TwitterException(e);
    } catch (IOException e) {
      throw new TwitterException(e);
    } finally {
      method.releaseConnection();
    }
  }
  
  public static void main(String[] argv) throws TwitterException {
    Api api = new Api();
    Status[] statuses = api.getPublicTimeline();
    for (Status status : statuses) {
      System.out.println(status);
    }
  }
}
