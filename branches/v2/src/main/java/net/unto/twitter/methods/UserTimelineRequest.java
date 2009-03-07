package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

import org.joda.time.DateTime;

public class UserTimelineRequest extends AbstractRequest<UserTimelineRequest> {

  public UserTimelineRequest() {
    path = "/statuses/user_timeline.json";
  }

  public UserTimelineRequest id(String id) {
    assert(id != null);
    path = String.format("/statuses/user_timeline/%s.json", id);
    return this;
  }
  
  public UserTimelineRequest since(DateTime since) {
    assert (since != null);
    return parameter("since", since.toString());
  }

  public UserTimelineRequest sinceId(long sinceId) {
    return parameter("since_id", Long.toString(sinceId));
  }

  public UserTimelineRequest count(int count) {
    return parameter("count", Integer.toString(count));
  }

  public UserTimelineRequest page(int page) {
    return parameter("page", Integer.toString(page));
  }

  public List<Status> get() throws TwitterException {
    return JsonUtil.newStatusList(getJson());
  }
}
