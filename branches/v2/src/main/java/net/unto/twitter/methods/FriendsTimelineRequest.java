package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

import org.joda.time.DateTime;

public class FriendsTimelineRequest extends AbstractRequest<FriendsTimelineRequest> {
  
  public FriendsTimelineRequest() {
    path = "/statuses/friends_timeline.json";
  }

  public FriendsTimelineRequest since(DateTime since) {
    assert (since != null);
    return parameter("since", since.toString());
  }

  public FriendsTimelineRequest sinceId(long sinceId) {
    return parameter("since_id", Long.toString(sinceId));
  }

  public FriendsTimelineRequest count(int count) {
    return parameter("count", Integer.toString(count));
  }

  public FriendsTimelineRequest page(int page) {
    return parameter("page", Integer.toString(page));
  }

  public List<Status> get() throws TwitterException {
    // TODO(dewitt): Must be authenticated
    return JsonUtil.newStatusList(getJson());
  }
}
