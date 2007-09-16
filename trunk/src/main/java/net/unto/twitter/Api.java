package net.unto.twitter;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
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
   * @return an array of {@link Status} instances
   * @throws TwitterException
   */
  public Status[] getPublicTimeline() throws TwitterException {
    return getPublicTimeline(null);
  }

  /**
   * Returns the 20 most recent statuses from non-protected users who have set a custom user icon.  Does not require authentication.
   * 
   * @param sinceId Optional.  Returns only public statuses with an ID greater than (that is, more recent than) the specified ID.
   * @return an array of {@link Status} instances
   * @throws TwitterException
   */
  public Status[] getPublicTimeline(Long sinceId) throws TwitterException {
    String url = "http://twitter.com/statuses/public_timeline.json";
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (sinceId != null) {
      parameters.add(new NameValuePair("since_id", sinceId.toString()));
    }
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newStatusArray(execute(method));
  }
  
  /**
   * Returns the 20 most recent statuses posted in the last 24 hours from the authenticating user and that user's friends.
   * 
   * @return an array of {@link Status} instances
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
   * @return an array of {@link Status} instances
   * @throws TwitterException
   */
  public Status[] getFriendsTimeline(String id, DateTime since, Integer page) throws TwitterException {
    requireCredentials();
    String url;
    if (id == null) {
      url = "http://twitter.com/statuses/friends_timeline.json";
    } else {
      url = String.format("http://twitter.com/statuses/friends_timeline/%s.json", id);
    }
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (since != null) {
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
   * @return an array of {@link Status} instances
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
   * @return an array of {@link Status} instances
   * @throws TwitterException
   */
  public Status[] getUserTimeline(String id, Integer count, DateTime since, Integer page) throws TwitterException {
    requireCredentials();
    String url;
    if (id == null) {
      url = "http://twitter.com/statuses/user_timeline.json";
    } else {
      url = String.format("http://twitter.com/statuses/user_timeline/%s.json", id);
    }
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (since != null) {
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
   * @return a {@link Status} instance
   * @throws TwitterException
   */
  public Status showStatus(Long id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    String url = String.format("http://twitter.com/statuses/show/%d.json", id);
    HttpMethod method = new GetMethod(url);
    return JsonUtil.newStatus(execute(method));
  }
  
  /**
   * Updates the authenticating user's status.
   * 
   * @param status Required.  The text of your status update.  Must not be more than 160 characters and should not be more than 140 characters to ensure optimal display.
   * @return a {@link Status} instance
   * @throws TwitterException
   */
  public Status updateStatus(String status) throws TwitterException {
    if (status == null) {
      throw new IllegalArgumentException("status required");
    }
    requireCredentials();
    String url = "http://twitter.com/statuses/update.json";
    HttpMethod method = new PostMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new NameValuePair("status", status));
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newStatus(execute(method));
  }
  
  /**
   * Returns the 20 most recent replies (status updates prefixed with @username posted by users who are friends with the user being replied to) to the authenticating user.  Replies are only available to the authenticating user; you can not request a list of replies to another user whether public or protected.
   * 
   * @return an array of {@link Status} instances
   * @throws TwitterException
   */
  public Status[] getReplies() throws TwitterException {
    return getReplies(null);
  }
  
  /**
   * Returns the 20 most recent replies (status updates prefixed with @username posted by users who are followers with the user being replied to) to the authenticating user.  Replies are only available to the authenticating user; you can not request a list of replies to another user whether public or protected.
   * 
   * @param page Optional. Retrieves the 20 next most recent replies.
   * @return an array of {@link Status} instances
   * @throws TwitterException
   */
  public Status[] getReplies(Integer page) throws TwitterException {
    requireCredentials();
    String url = "http://twitter.com/statuses/replies.json";
    HttpMethod method = new GetMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (page != null) {
      parameters.add(new NameValuePair("page", page.toString()));
    }
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newStatusArray(execute(method));
  }
  
  /**
   * Destroys the status specified by the required ID parameter.  The authenticating user must be the author of the specified status.
   * 
   * @param id Required.  The ID of the status to destroy.
   * @return a {@link Status} instance
   * @throws TwitterException
   */
  public Status destroyStatus(Long id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    requireCredentials();
    String url = String.format("http://twitter.com/statuses/destroy/%d.json", id);
    System.out.println(url);
    HttpMethod method = new PostMethod(url);
    return JsonUtil.newStatus(execute(method));
  }
  
  /**
   * Returns up to 100 of the people the authenticating user follows who have most recently updated, each with current status inline. 
   * 
   * @return An array of {@link User} instances
   * @throws TwitterException
   */
  public User[] getFollowing() throws TwitterException {
    return getFollowing(null);
  }
  
  /**
   * Returns up to 100 of the people the authenticating user follows who have most recently updated, each with current status inline. 
   * 
   * @param id Optional.  The ID or screen name of the user for whom to request a list of people they follow
   * @return An array of {@link User} instances
   * @throws TwitterException
   */
  public User[] getFollowing(String id) throws TwitterException {
    requireCredentials();
    String url;
    if (id == null) {
      url = "http://twitter.com/statuses/friends.json";
    } else {
      url = String.format("http://twitter.com/statuses/friends/%s.json", id);
    }
    HttpMethod method = new GetMethod(url);
    return JsonUtil.newUserArray(execute(method));
  }

  /**
   * Returns the authenticating user's followers, each with current status inline. 
   * 
   * @return An array of {@link User} instances
   * @throws TwitterException
   */
  public User[] getFollowers() throws TwitterException {
    return getFollowers(false);
  }
  
  /**
   * Returns the authenticating user's followers, each with current status inline. 
   * 
   * @param lite Optional.  Prevents the inline inclusion of current status. 
   * @return An array of {@link User} instances
   * @throws TwitterException
   */
  public User[] getFollowers(Boolean lite) throws TwitterException {
    requireCredentials();
    String url = "http://twitter.com/statuses/followers.json";
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (lite != null && lite == Boolean.TRUE) {
      parameters.add(new NameValuePair("lite", "true"));
    }
    HttpMethod method = new GetMethod(url);
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newUserArray(execute(method));
  }
  
  /**
   * Returns a list of the users currently featured on the site with their current statuses inline.
   * 
   * @return An array of {@link User} instances
   * @throws TwitterException
   */
  public User[] getFeatured() throws TwitterException {
    String url = "http://twitter.com/statuses/featured.json";
    HttpMethod method = new GetMethod(url);
    return JsonUtil.newUserArray(execute(method));
  }
 
  /**
   * Returns extended information of a given user, specified by ID or screen name as per the required id parameter below.
   * 
   * @param id Required.  The ID or screen name of a user. 
   * @return A {@link User} instance
   * @throws TwitterException
   */
  public User showUser(String id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    String url = String.format("http://twitter.com/users/show/%s.json", id);
    HttpMethod method = new GetMethod(url);
    return JsonUtil.newUser(execute(method));
  }

  /**
   * Returns a list of the 20 most recent direct messages sent to the authenticating user. 
   *  
   * @return An array of {@link DirectMessage} instances
   * @throws TwitterException
   */
  public DirectMessage[] getDirectMessages() throws TwitterException {
    return getDirectMessages(null, null, null);
  }
  
  /**
   * Returns a list of the 20 most recent direct messages sent to the authenticating user. 
   *  
   * @param since Optional.  Narrows the resulting list of direct messages to just those sent after the specified HTTP-formatted date.
   * @param sinceId Optional.  Returns only direct messages with an ID greater than (that is, more recent than) the specified ID.
   * @param page Optional. Retrieves the 20 next most recent direct messages. 
   * @return An array of {@link DirectMessage} instances
   * @throws TwitterException
   */
  public DirectMessage[] getDirectMessages(DateTime since, String sinceId, Integer page) throws TwitterException {
    requireCredentials();
    String url = "http://twitter.com/direct_messages.json";
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (since != null) {
      parameters.add(new NameValuePair("since", since.toString()));
    }
    if (sinceId != null) {
      parameters.add(new NameValuePair("since_id", sinceId));
    }
    if (page != null) {
      parameters.add(new NameValuePair("page", page.toString()));
    }
    HttpMethod method = new GetMethod(url);
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newDirectMessageArray(execute(method));
  }
  
  /**
   * Returns a list of the 20 most recent direct messages sent by the authenticating user.
   * 
   * @throws TwitterException
   */
  public DirectMessage[] getSentDirectMessages() throws TwitterException {
    return getSentDirectMessages(null, null, null);
  }
  
  /**
   * Returns a list of the 20 most recent direct messages sent by the authenticating user.
   * 
   * @param since Optional.  Narrows the resulting list of direct messages to just those sent after the specified HTTP-formatted date.
   * @param sinceId Optional.  Returns only sent direct messages with an ID greater than (that is, more recent than) the specified ID.
   * @param page Optional. Retrieves the 20 next most recent direct messages sent. 
   * @return An array of {@link DirectMessage} instances
   * @throws TwitterException
   */
  public DirectMessage[] getSentDirectMessages(DateTime since, String sinceId, Integer page) throws TwitterException {
    requireCredentials();
    String url = "http://twitter.com/direct_messages/sent.json";
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    if (since != null) {
      parameters.add(new NameValuePair("since", since.toString()));
    }
    if (sinceId != null) {
      parameters.add(new NameValuePair("since_id", sinceId));
    }
    if (page != null) {
      parameters.add(new NameValuePair("page", page.toString()));
    }
    HttpMethod method = new GetMethod(url);
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newDirectMessageArray(execute(method));
  }
  
  /**
   * Sends a new direct message to the specified user from the authenticating user.
   * 
   * @param user Required.  The ID or screen name of the recipient user.
   * @param text  Required.  The text of your direct message.
   * @return A {@link DirectMessage} instance
   */
  public DirectMessage newDirectMessage(String user, String text) throws TwitterException {
    if (user == null) {
      throw new IllegalArgumentException("user required");
    }
    if (text == null) {
      throw new IllegalArgumentException("text required");
    }
    requireCredentials();
    String url = "http://twitter.com/direct_messages/new.json";
    HttpMethod method = new PostMethod(url);
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new NameValuePair("user", user));
    parameters.add(new NameValuePair("text", text));
    method.setQueryString((NameValuePair[])parameters.toArray(new NameValuePair[parameters.size()]));
    return JsonUtil.newDirectMessage(execute(method));
  }
  
  
  /**
   * Destroys the direct message specified in the required ID parameter.  The authenticating user must be the recipient of the specified direct message.
   * 
   * @param id Required.  The ID of the direct message to destroy. 
   * @return A {@link DirectMessage} instance
   * @throws TwitterException
   */
  public DirectMessage destroyDirectMessage(Long id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    requireCredentials();
    String url = String.format("http://twitter.com/direct_messages/destroy/%d.json", id);
    System.out.println(url);
    HttpMethod method = new PostMethod(url);
    return JsonUtil.newDirectMessage(execute(method));
  }
  
  
  /**
   * Adds the user specified in the ID parameter to the list of users that the authenticating user is following.
   * 
   * @param id Required.  The ID or screen name of the user to start following. 
   * @return A {@link User} instance
   * @throws TwitterException
   */
  public User startFollowing(String id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    requireCredentials();
    // TODO(dewitt): Implement
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Removes the user specified in the ID parameter from the list of users that the authenticating user is following.
   * 
   * @param id Required.  The ID or screen name of the user to stop following. 
   * @return A {@link User} instance
   * @throws TwitterException
   */
  public User stopFollowing(String id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    requireCredentials();
    // TODO(dewitt): Implement
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Returns the 20 most recent favorite statuses for the authenticating user or user specified by the ID parameter.
   * 
   * @return an array of {@link Status} instances
   */
  public Status[] getFavorites() throws TwitterException {
    return getFavorites(null, null);
  }
  
  /**
   * Returns the 20 most recent favorite statuses for the authenticating user or user specified by the ID parameter.
   * 
   * @param id Optional.  The ID or screen name of the user for whom to request a list of favorite statuses.   
   * @param page Optional. Retrieves the 20 next most recent favorite statuses.
   * @return an array of {@link Status} instances
   */
  public Status[] getFavorites(String id, Integer page) throws TwitterException {
    // TODO(dewitt): Implement
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Favorites the status specified in the ID parameter as the authenticating user.  
   * 
   * @param id Required.  The ID of the status to favorite.
   * @return a {@link Status} instance
   * @throws TwitterException
   */
  public Status createFavorite(String id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    // TODO(dewitt): Implement
    throw new TwitterException("Method not implemented");
  }
  
  /**
   * Un-favorites the status specified in the ID parameter as the authenticating user. 
   * 
   * @param id Required.  The ID of the status to un-favorite.
   * @return a {@link Status} instance
   * @throws TwitterException
   */
  public Status destroyFavorite(String id) throws TwitterException {
    if (id == null) {
      throw new IllegalArgumentException("id required");
    }
    // TODO(dewitt): Implement
    throw new TwitterException("Method not implemented");
  }
  
  
  /**
   * Use the specified username and password to authenticate as a user.
   * 
   * @param username the Twitter username
   * @param password the Twitter password
   */
  public void setCredentials(String username, String password) {
    if (username == null) {
      throw new IllegalArgumentException("Username must not be null");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password must not be null");
    }
    setCredentials(new UsernamePasswordCredentials(username, password));
  }
  
  /**
   * Use the specified {@link Credentials} to authenticate as a user.
   * 
   * @param credentials the Twitter username and passwords credentials
   */
  public void setCredentials(Credentials credentials) {
     this.credentials = credentials;
  }
  
  private final AuthScope AUTH_SCOPE = new AuthScope("twitter.com", 80, AuthScope.ANY_REALM);
  
  /**
   * Clear the stored username and password.
   */
  public void clearCredentials() {
    this.credentials = null;
  }

  private boolean hasCredentials() {
    return this.credentials != null;
  }
  
  private Credentials getCredentials() {
    return this.credentials;
  }
  
  private Credentials credentials = null;
  
  private HttpConnectionManager manager = null;
  
  private HttpConnectionManager getHttpConnectionManager() {
    if (this.manager == null) {
      this.manager = new MultiThreadedHttpConnectionManager();
    }
    return this.manager;
  }
  
  protected void setHttpConnectionManager(HttpConnectionManager manager) {
    this.manager = manager;
  }
  
  private String execute(HttpMethod method) throws TwitterException {
    method.getParams( ).setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
    HttpClient httpClient = new HttpClient(getHttpConnectionManager());
    if (hasCredentials()) {
      httpClient.getState( ).setCredentials(AUTH_SCOPE, getCredentials());
      httpClient.getParams().setAuthenticationPreemptive(true);
    } else {
      httpClient.getParams().setAuthenticationPreemptive(false);
    }
    try {
      int statusCode = httpClient.executeMethod(method);
      if (statusCode != HttpStatus.SC_OK) {
        String error = String.format("Expected 200 OK. Received %d %s", statusCode, HttpStatus.getStatusText(statusCode));
        throw new TwitterException(error);
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
  
  private void requireCredentials() throws TwitterException {
    if (!hasCredentials()) {
      throw new TwitterException("Authentication required.  Please call api.setCredentials first.");
    }
  }

  public static void main(String[] argv) throws TwitterException {
    Api api = new Api();

   for (DirectMessage directMessage : api.getDirectMessages( )) {
      System.out.println(directMessage);
   }
//    for (Status status : api.getReplies()) {
//     System.out.println(status);
//    }
//    api.updateStatus("Моё судно на воздушной подушке полно угрей");
//    Status[] statuses = api.getReplies();
 //   for (Status status : statuses) {
 //     System.out.println(status);
//      System.out.println(status.getText());
//    }
//    System.out.println(api.destroyStatus(271586842L));
//    System.out.println(api.showUser("dewitt"));
//    for (User user : api.getFollowers()) {
//      System.out.println(user);
//    }
  }
}
