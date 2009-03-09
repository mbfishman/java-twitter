package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.User;

/**
 * Returns the authenticating user's followers, each with current status inline.
 * They are ordered by the order in which they joined Twitter (this is going to
 * be changed).
 */
public final class FriendsRequest extends AbstractRequest {

  public static Builder builder() {
    return new Builder();
  }
  
  FriendsRequest(Builder builder) throws TwitterException {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder() {
      path = "/statuses/friends.json";
      authorizationRequired = true;
    }
    
    /**
     * The ID or screen name of the user for whom to request a list of followers.
     * 
     * @param id The ID or screen name of the user for whom to request a list of
     *        followers.
     * @return {@link FriendsRequest}
     */
    public Builder id(String id) {
      assert (id != null);
      path = String.format("/statuses/friends/%s.json", id);
      authorizationRequired = false; 
      return this;
    }
    
    /**
     * Retrieves the next 100 followers.
     * 
     * @param page Retrieves the next 100 followers.
     * @return {@link FriendsRequest}
     */
    public Builder page(int page) {
      return parameter("page", Integer.toString(page));
    }
    
    public FriendsRequest build() throws TwitterException {
      return new FriendsRequest(this);
    }    
  }



  /**
   * Returns the authenticating user's followers, each with current status
   * inline. They are ordered by the order in which they joined Twitter (this is
   * going to be changed).
   * 
   * @return {@link List} of {@link User}
   * @throws TwitterException
   */
  public List<User> get() throws TwitterException {
    return JsonUtil.newUserList(getJson());
  }
}
