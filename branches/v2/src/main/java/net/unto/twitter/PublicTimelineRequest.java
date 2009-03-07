package net.unto.twitter;

import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.UtilProtos.Url;

import java.util.List;

public class PublicTimelineRequest extends AbstractRequest {

  private String baseUrl = "http://twitter.com/statuses/public_timeline.json";
  
  PublicTimelineRequest(HttpManager httpManager) {
    this.httpManager = httpManager;
  }
  
  public PublicTimelineRequest sinceId(long sinceId) {
    AddParameter("since_id", Long.toString(sinceId));
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