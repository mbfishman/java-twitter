package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.User;

public class FriendsRequest extends AbstractRequest<FriendsRequest> {

  public FriendsRequest() {
    path = "/statuses/friends.json";
    authorizationRequired = true;
  }

  public FriendsRequest id(String id) {
    assert (id != null);
    path = String.format("/statuses/friends/%s.json", id);
    authorizationRequired = false; 
    return this;
  }

  public FriendsRequest page(int page) {
    return parameter("page", Integer.toString(page));
  }

  public List<User> get() throws TwitterException {
    return JsonUtil.newUserList(getJson());
  }
}
