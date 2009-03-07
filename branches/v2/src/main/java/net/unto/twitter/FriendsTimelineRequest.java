package net.unto.twitter;

import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.UtilProtos.Url;

import org.joda.time.DateTime;

import java.util.List;

public class FriendsTimelineRequest extends AbstractRequest {

  private String baseUrl = "http://twitter.com/statuses/friends_timeline.json";

  FriendsTimelineRequest(HttpManager httpManager) {
    // TODO(dewitt): Must be authenticated
    this.httpManager = httpManager;
  }

  public FriendsTimelineRequest since(DateTime since) {
    assert (since != null);
    AddParameter("since", since.toString());
    return this;
  }

  public FriendsTimelineRequest sinceId(long sinceId) {
    AddParameter("since_id", Long.toString(sinceId));
    return this;
  }

  public FriendsTimelineRequest count(int count) {
    AddParameter("count", Integer.toString(count));
    return this;
  }

  public FriendsTimelineRequest page(int page) {
    AddParameter("page", Integer.toString(page));
    return this;
  }

  public List<Status> get() throws TwitterException {
    Url url = Url.newBuilder().setBaseUrl(baseUrl).addAllParameters(parameters)
        .build();
    return JsonUtil.newStatusList(httpManager.get(url));
  }
}
