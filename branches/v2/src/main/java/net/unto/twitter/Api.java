package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;
import net.unto.twitter.UtilProtos.Url.Scheme;
import net.unto.twitter.methods.CreateFriendshipRequest;
import net.unto.twitter.methods.DestroyDirectMessageRequest;
import net.unto.twitter.methods.DestroyFriendshipRequest;
import net.unto.twitter.methods.DestroyStatusRequest;
import net.unto.twitter.methods.DirectMessagesRequest;
import net.unto.twitter.methods.FollowerIdsRequest;
import net.unto.twitter.methods.FollowersRequest;
import net.unto.twitter.methods.FriendIdsRequest;
import net.unto.twitter.methods.FriendsRequest;
import net.unto.twitter.methods.FriendsTimelineRequest;
import net.unto.twitter.methods.FriendshipExistsRequest;
import net.unto.twitter.methods.NewDirectMessageRequest;
import net.unto.twitter.methods.PublicTimelineRequest;
import net.unto.twitter.methods.RepliesRequest;
import net.unto.twitter.methods.Request;
import net.unto.twitter.methods.SentDirectMessagesRequest;
import net.unto.twitter.methods.ShowStatusRequest;
import net.unto.twitter.methods.ShowUserRequest;
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
 * List&lt;Status&gt; statuses = api.publicTimeline().build().get();
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
 * Status = api.updateStatus(&quot;Hello Twitter&quot;).inReplyToStatusId(12345).build()
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
   * <code>Status status = api.destroyStatus(12345).build().post();</code>
   * </p>
   * 
   * @param id The ID of the status to destroy.
   * @return {@link DestroyStatusRequest.Builder}
   */
  public DestroyStatusRequest.Builder destroyStatus(long id) {
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
   * <code>List<User> followers = api.followers().build().get();</code>
   * </p>
   * 
   * @return {@link FollowersRequest.Builder}
   */
  public FollowersRequest.Builder followers() {
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
   * <code>List<User> friends = api.friends().build().get();</code>
   * </p>
   * 
   * @return {@link FriendsRequest.Builder}
   */
  public FriendsRequest.Builder friends() {
    FriendsRequest.Builder builder = FriendsRequest.builder();
    setDefaults(builder);
    return builder;
  }

  public FriendsTimelineRequest.Builder friendsTimeline() {
    FriendsTimelineRequest.Builder builder = FriendsTimelineRequest.builder();
    setDefaults(builder);
    return builder;
  }

  public PublicTimelineRequest.Builder publicTimeline() {
    PublicTimelineRequest.Builder builder = PublicTimelineRequest.builder();
    setDefaults(builder);
    return builder;
  }

  public RepliesRequest.Builder replies() {
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

  public ShowStatusRequest.Builder showStatus(long id) {
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
   * <code>Status status = api.updateStatus("Hello Twitter").build().post();</code>
   * </p>
   * 
   * @param status The text of your status update. Should not be more than 140
   *        characters.
   * @return {@link UpdateStatusRequest.Builder}
   */
  public UpdateStatusRequest.Builder updateStatus(String status) {
    UpdateStatusRequest.Builder builder = UpdateStatusRequest.builder(status);
    setDefaults(builder);
    return builder;
  }

  public UserTimelineRequest.Builder userTimeline() {
    UserTimelineRequest.Builder builder = UserTimelineRequest.builder();
    setDefaults(builder);
    return builder;
  }

  /**
   * Returns extended information of a given user, specified by ID or screen
   * name as per the required id parameter below. This information includes
   * design settings, so third party developers can theme their widgets
   * according to a given user's preferences. You must be properly authenticated
   * to request the page of a protected user.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>User user = api.showUser().id("dewitt").build().get();</code>
   * </p>
   * 
   * @return {@link ShowUserRequest.Builder}
   */
  public ShowUserRequest.Builder showUser() {
    ShowUserRequest.Builder builder = ShowUserRequest.builder();
    setDefaults(builder);
    return builder;
  }

  /**
   * Returns a list of the 20 most recent direct messages sent to the
   * authenticating user.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>List<DirectMessages> directMessages = api.directMessages().build().get();</code>
   * </p>
   * 
   * @return {@link DirectMessagesRequest.Builder}
   */
  public DirectMessagesRequest.Builder directMessages() {
    DirectMessagesRequest.Builder builder = DirectMessagesRequest.builder();
    setDefaults(builder);
    return builder;
  }

  /**
   * Returns a list of the 20 most recent direct messages sent by the
   * authenticating user.
   * 
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>List<DirectMessages> directMessages = api.sentDirectMessages().build().get();</code>
   * </p>
   * 
   * @return {@link SentDirectMessagesRequest.Builder}
   */
  public SentDirectMessagesRequest.Builder sentDirectMessages() {
    SentDirectMessagesRequest.Builder builder = SentDirectMessagesRequest
        .builder();
    setDefaults(builder);
    return builder;
  }

  /**
   * Sends a new direct message to the specified user from the authenticating
   * user.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>DirectMessage directMessage = api.newDirectMessage("dewitt", "Hello DeWitt").build().post();</code>
   * </p>
   * 
   * @param status The text of your status update. Should not be more than 140
   *        characters.
   * @return {@link NewDirectMessageRequest.Builder}
   */
  public NewDirectMessageRequest.Builder newDirectMessage(String user,
      String status) {
    NewDirectMessageRequest.Builder builder = NewDirectMessageRequest.builder(
        user, status);
    setDefaults(builder);
    return builder;
  }

  /**
   * Destroys the direct message specified in the required ID parameter. The
   * authenticating user must be the recipient of the specified direct message.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>DirectMessage directMessage = api.destroyDirectMessage(12345).build().post();</code>
   * </p>
   * 
   * @param id The ID of the direct message to destroy.
   * @return {@link DestroyDirectMessageRequest.Builder}
   */
  public DestroyDirectMessageRequest.Builder destroyDirectMessage(long id) {
    DestroyDirectMessageRequest.Builder builder = DestroyDirectMessageRequest
        .builder(id);
    setDefaults(builder);
    return builder;
  }

  /**
   * Befriends the user specified in the ID parameter as the authenticating
   * user.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>User user = api.createFriendship("dewitt").build().post();</code>
   * </p>
   * 
   * @param id The ID or screen name of the user to befriend
   * @return {@link CreateFriendshipRequest.Builder}
   */
  public CreateFriendshipRequest.Builder createFriendship(String id) {
    CreateFriendshipRequest.Builder builder = CreateFriendshipRequest
        .builder(id);
    setDefaults(builder);
    return builder;
  }

  /**
   * Discontinues friendship with the user specified in the ID parameter as the
   * authenticating user
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>User user = api.destroyFriendship("dewitt").build().post();</code>
   * </p>
   * 
   * @param id The ID or screen name of the user with whom to discontinue
   *        friendship.
   * @return {@link DestroyFriendshipRequest.Builder}
   */
  public DestroyFriendshipRequest.Builder destroyFriendship(String id) {
    DestroyFriendshipRequest.Builder builder = DestroyFriendshipRequest
        .builder(id);
    setDefaults(builder);
    return builder;
  }

  /**
   * Tests if a friendship exists between two users.
   * <p>
   * Example usage:
   * </p>
   * <p>
   * <code>boolean friendshipExists = api.friendshipExists("ev", "biz).build().post();</code>
   * </p>
   * 
   * @param userA The ID or screen_name of the first user to test friendship
   *        for.
   * @param userB The ID or screen_name of the second user to test friendship
   *        for.
   * @return {@link FriendshipExistsRequest.Builder}
   */
  public FriendshipExistsRequest.Builder friendshipExists(String userA,
      String userB) {
    FriendshipExistsRequest.Builder builder = FriendshipExistsRequest.builder(
        userA, userB);
    setDefaults(builder);
    return builder;
  }

  /**
   * Returns an array of numeric IDs for every user the specified user is following.
   * 
   * @return {@link FriendIdsRequest.Builder}
   */
  public FriendIdsRequest.Builder friendIds() {
    FriendIdsRequest.Builder builder = FriendIdsRequest.builder();
    setDefaults(builder);
    return builder;
  }
  
  /**
   * Returns an array of numeric IDs for every user the specified user is followed by.
   * 
   * @return {@link FollowerIdsRequest.Builder}
   */
  public FollowerIdsRequest.Builder followerIds() {
    FollowerIdsRequest.Builder builder = FollowerIdsRequest.builder();
    setDefaults(builder);
    return builder;
  }
}
