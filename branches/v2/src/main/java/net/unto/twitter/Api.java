package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;
import net.unto.twitter.UtilProtos.Url.Scheme;
import net.unto.twitter.methods.DestroyStatusRequest;
import net.unto.twitter.methods.FollowersRequest;
import net.unto.twitter.methods.FriendsRequest;
import net.unto.twitter.methods.FriendsTimelineRequest;
import net.unto.twitter.methods.PublicTimelineRequest;
import net.unto.twitter.methods.RepliesRequest;
import net.unto.twitter.methods.Request;
import net.unto.twitter.methods.ShowStatusRequest;
import net.unto.twitter.methods.UpdateStatusRequest;
import net.unto.twitter.methods.UserTimelineRequest;

/**
 * Instances of the Api class provide access to the Twitter web service.
 * <p>
 * The Api class acts a factory for individual Api requests. The Api class and
 * request methods attempt to provide sensible defaults when possible, and rely
 * on standard builder patterns to customize each as required.
 * </p>
 * <p>
 * Example usage:
 * <p>
 * 
 * <pre>
 * Api api = Api.DEFAULT_API;
 * List&lt;Status&gt; statuses = api.PublicTimeline().build().get();
 * for (Status status : statuses) {
 *   System.out.println(status.getText());
 * }
 * </pre>
 * 
 * <p>
 * A more involved example:
 * </p>
 * 
 * <pre>
 * Api api = Api.builder().username(&quot;username&quot;).password(&quot;password&quot;).build();
 * Status = api.UpdateStatus(&quot;Hello Twitter&quot;).inReplyToStatusId(12345).build()
 *     .post();
 * System.out.println(status.getText());
 * </pre>
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
public class Api {

  public static class Builder {

    private String host = DEFAULT_HOST;

    private HttpManager httpManager = DEFAULT_HTTP_MANAGER;
    private String password = null;
    private int port = DEFAULT_PORT;
    private Url.Scheme scheme = DEFAULT_SCHEME;
    private String username = null;

    public Api build() {
      if ((username != null) && (password == null)) {
        throw new IllegalStateException(
            "Password must be set if username is set.");
      }
      if ((password != null) && (username == null)) {
        throw new IllegalStateException(
            "Username must be set if password is set.");
      }
      if ((username != null) && (httpManager != null)) {
        throw new IllegalStateException(
            "Only one of httpManager and username can be set.");
      }
      return new Api(this);
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder httpManager(HttpManager httpManager) {
      this.httpManager = httpManager;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder scheme(Url.Scheme scheme) {
      this.scheme = scheme;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }
  }

  public static final Api DEFAULT_API = Api.builder().build();

  public static final String DEFAULT_HOST = "twitter.com";

  public static final HttpManager DEFAULT_HTTP_MANAGER = TwitterHttpManager
      .builder().build();

  public static final int DEFAULT_PORT = 80;

  public static final Url.Scheme DEFAULT_SCHEME = Url.Scheme.HTTP;

  public static Builder builder() {
    return new Builder();
  }

  private String host;

  private HttpManager httpManager = null;

  private int port;

  private Scheme scheme;

  private Api(Builder builder) {
    assert (builder.host != null);
    assert (builder.port > 0);
    assert (builder.scheme != null);
    if (builder.httpManager != null) {
      this.httpManager = builder.httpManager;
    } else if (builder.username != null && builder.password != null) {
      this.httpManager = TwitterHttpManager.builder()
          .username(builder.username).password(builder.password).build();
    } else {
      this.httpManager = TwitterHttpManager.builder().build();
    }
    host = builder.host;
    port = builder.port;
    scheme = builder.scheme;
  }

  /**
   * Destroys the status specified by the required ID parameter. The
   * authenticating user must be the author of the specified status.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>Status status = api.DestroyStatus(12345).build().post();</code>
   * </p>
   * 
   * @param id The ID of the status to destroy.
   * @return {@link DestroyStatusRequest.Builder}
   */
  public DestroyStatusRequest.Builder DestroyStatus(long id) {
    DestroyStatusRequest.Builder builder = DestroyStatusRequest.builder(id);
    setDefaults(builder);
    return builder;
  }

  /**
   * Returns the authenticating user's followers, each with current status
   * inline. They are ordered by the order in which they joined Twitter (this is
   * going to be changed).
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>List<User> followers = api.Followers().build().get();</code>
   * </p>
   * 
   * @return {@link FollowersRequest.Builder}
   */
  public FollowersRequest.Builder Followers() {
    FollowersRequest.Builder builder = FollowersRequest.builder();
    setDefaults(builder);
    return builder;
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
   * <code>List<User> friends = api.Friends().build().get();</code>
   * </p>
   * 
   * @return {@link FriendsRequest.Builder}
   */
  public FriendsRequest.Builder Friends() {
    FriendsRequest.Builder builder = FriendsRequest.builder();
    setDefaults(builder);
    return builder;
  }

  public FriendsTimelineRequest.Builder FriendsTimeline() {
    FriendsTimelineRequest.Builder builder = FriendsTimelineRequest.builder();
    setDefaults(builder);
    return builder;
  }

  public PublicTimelineRequest.Builder PublicTimeline() {
    PublicTimelineRequest.Builder builder = PublicTimelineRequest.builder();
    setDefaults(builder);
    return builder;
  }

  public RepliesRequest.Builder Replies() {
    RepliesRequest.Builder builder = RepliesRequest.builder();
    setDefaults(builder);
    return builder;
  }

   void setDefaults(Request.Builder builder) {
    builder.httpManager(httpManager);
    builder.host(host);
    builder.port(port);
    builder.scheme(scheme);
  }

  public ShowStatusRequest.Builder ShowStatus(long id) {
    ShowStatusRequest.Builder builder = ShowStatusRequest.builder(id);
    setDefaults(builder);
    return builder;
  }

  /**
   * Updates the authenticating user's status. Requires the status parameter
   * specified below. Request must be a POST. A status update with text
   * identical to the authenticating user's current status will be ignored.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>Status status = api.UpdateStatus("Hello Twitter").build().post();</code>
   * </p>
   * 
   * @param status The text of your status update. Should not be more than 140
   *        characters.
   * @return {@link UpdateStatusRequest.Builder}
   */
  public UpdateStatusRequest.Builder UpdateStatus(String status) {
    UpdateStatusRequest.Builder builder = UpdateStatusRequest.builder(status);
    setDefaults(builder);
    return builder;
  }

  public UserTimelineRequest.Builder UserTimeline() {
    UserTimelineRequest.Builder builder = UserTimelineRequest.builder();
    setDefaults(builder);
    return builder;
  }
}
