package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterProtos.User;

/**
 * Tests if a friendship exists between two users.
 */
public final class FriendshipExistsRequest extends AbstractRequest {

  public static Builder builder(String userA, String userB) {
    return new Builder(userA, userB);
  }

  FriendshipExistsRequest(Builder builder) {
    super(builder);
  }

  public static final class Builder extends AbstractRequest.Builder<Builder> {

    /**
     * @param userA The ID or screen_name of the first user to test friendship
     *        for.
     * @param userB The ID or screen_name of the second user to test friendship
     *        for.
     */
    Builder(String userA, String userB) {
      assert (userA != null);
      assert (userB != null);
      parameter("user_a", userA);
      parameter("user_b", userB);
      path("/friendships/exists.json");
      authorizationRequired(true);
    }

    public FriendshipExistsRequest build() {
      return new FriendshipExistsRequest(this);
    }
  }

  public boolean get() {
    return Boolean.parseBoolean(getJson());
  }
}
