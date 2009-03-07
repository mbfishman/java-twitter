package net.unto.twitter;

import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.UtilProtos.Url;

import org.joda.time.DateTime;

import java.util.List;

public class UserTimelineRequest extends AbstractRequest {

  private String baseUrl = "http://twitter.com/statuses/user_timeline.json";

  UserTimelineRequest(HttpManager httpManager) {
    this.httpManager = httpManager;
  }

  public UserTimelineRequest id(String id) {
    assert(id != null);
    baseUrl = 
        String.format("http://twitter.com/statuses/user_timeline/%s.json", id);
    return this;
  }
  
  public UserTimelineRequest since(DateTime since) {
    assert (since != null);
    AddParameter("since", since.toString());
    return this;
  }

  public UserTimelineRequest sinceId(long sinceId) {
    AddParameter("since_id", Long.toString(sinceId));
    return this;
  }

  public UserTimelineRequest count(int count) {
    AddParameter("count", Integer.toString(count));
    return this;
  }

  public UserTimelineRequest page(int page) {
    AddParameter("page", Integer.toString(page));
    return this;
  }

  public List<Status> get() throws TwitterException {
    Url url = Url.newBuilder()
        .setBaseUrl(baseUrl)
        .addAllParameters(parameters)
        .build();
    return JsonUtil.newStatusList(httpManager.get(url));
  }
}
