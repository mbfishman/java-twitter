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
public class FollowersRequest extends AbstractRequest<FollowersRequest> {

  public FollowersRequest() {
    path = "/statuses/followers.json";
  }

  /**
   * The ID or screen name of the user for whom to request a list of followers.
   * 
   * @param id The ID or screen name of the user for whom to request a list of
   *        followers.
   * @return {@link FollowersRequest}
   */
  public FollowersRequest id(String id) {
    assert (id != null);
    path = String.format("statuses/followers/%s.json", id);
    return this;
  }

  /**
   * Retrieves the next 100 followers.
   * 
   * @param page Retrieves the next 100 followers.
   * @return {@link FollowersRequest}
   */
  public FollowersRequest page(int page) {
    return parameter("page", Integer.toString(page));
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
