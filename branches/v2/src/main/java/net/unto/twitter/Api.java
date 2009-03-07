package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;
import net.unto.twitter.methods.DestroyStatusRequest;
import net.unto.twitter.methods.FollowersRequest;
import net.unto.twitter.methods.FriendsRequest;
import net.unto.twitter.methods.FriendsTimelineRequest;
import net.unto.twitter.methods.PublicTimelineRequest;
import net.unto.twitter.methods.RepliesRequest;
import net.unto.twitter.methods.ShowStatusRequest;
import net.unto.twitter.methods.UpdateStatusRequest;
import net.unto.twitter.methods.UserTimelineRequest;

/**
 * Instances of the Api class provide access to the Twitter web service.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */

public class Api {

  public final static Url DEFAULT_BASE_URL = 
    Url.newBuilder()
      .setScheme(Url.Scheme.HTTP)
      .setHost("twitter.com")
      .setPort(80)
      .setPath("/")
      .build();
  
  public static class Builder {

    public Api build() throws TwitterException {
      if ((username != null) && (password == null)) {
        throw new TwitterException("Password must be set if username is set.");
      }
      if ((password != null) && (username == null)) {
        throw new TwitterException("Username must be set if password is set.");
      }
      if ((username != null) && (httpManager != null)) {
        throw new TwitterException(
            "Only one of httpManager and username can be set.");
      }
      return new Api(this);
    }

    private HttpManager httpManager = null;
    private String username = null;
    private String password = null;
    private Url baseUrl = DEFAULT_BASE_URL;

    public Builder httpManager(HttpManager httpManager) {
      this.httpManager = httpManager;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }
    
    public Builder baseUrl(Url baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  private Url baseUrl = null;

  private Api(Builder builder) {
    if (builder.httpManager != null) {
      this.httpManager = builder.httpManager;
    } else if (builder.username != null && builder.password != null) {
      this.httpManager = TwitterHttpManager.builder().username(builder.username).password(builder.password).build();
    } else {
      this.httpManager = TwitterHttpManager.builder().build();
    }
    assert(builder.baseUrl != null);
    this.baseUrl = builder.baseUrl;
  }

  private HttpManager httpManager = null;

  public PublicTimelineRequest PublicTimeline() {
    return new PublicTimelineRequest().httpManager(httpManager).baseUrl(baseUrl);
  }

  public FriendsTimelineRequest FriendsTimeline() {
    return new FriendsTimelineRequest().httpManager(httpManager).baseUrl(baseUrl);
  }

  public UserTimelineRequest UserTimeline() {
    return new UserTimelineRequest().httpManager(httpManager).baseUrl(baseUrl);
  }

  public ShowStatusRequest ShowStatus(long id) {
    return new ShowStatusRequest(id).httpManager(httpManager).baseUrl(baseUrl);
  }

  public UpdateStatusRequest UpdateStatus(String status) {
    return new UpdateStatusRequest(status).httpManager(httpManager).baseUrl(baseUrl);
  }

  public RepliesRequest Replies() {
    return new RepliesRequest().httpManager(httpManager).baseUrl(baseUrl);
  }

  /**
   * Destroys the status specified by the required ID parameter. The
   * authenticating user must be the author of the specified status.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>Status status = api.DestroyStatus(12345).post();</code>
   * </p>
   * 
   * @param id The ID of the status to destroy.
   * @return {@link DestroyStatusRequest}
   */
  public DestroyStatusRequest DestroyStatus(long id) {
    return new DestroyStatusRequest(id).httpManager(httpManager).baseUrl(baseUrl);
  }

  /**
   * Returns the authenticating user's friends, each with current status inline.
   * They are ordered by the order in which they were added as friends. It's
   * also possible to request another user's recent friends list via the id
   * parameter below.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>List<User> friends = api.Friends().get();</code>
   * </p>
   * 
   * @return {@link FriendsRequest}
   */
  public FriendsRequest Friends() {
    return new FriendsRequest().httpManager(httpManager).baseUrl(baseUrl);
  }

  /**
   * Returns the authenticating user's followers, each with current status
   * inline. They are ordered by the order in which they joined Twitter (this is
   * going to be changed).
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>List<User> followers = api.Followers().get();</code>
   * </p>
   * 
   * @return {@link FollowersRequest}
   */
  public FollowersRequest Followers() {
    return new FollowersRequest().httpManager(httpManager).baseUrl(baseUrl);
  }
}
