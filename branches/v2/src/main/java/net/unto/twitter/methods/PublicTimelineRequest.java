package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public class PublicTimelineRequest extends AbstractRequest<PublicTimelineRequest> {

  public PublicTimelineRequest() {
    path = "/statuses/public_timeline.json";
  }
 
  public PublicTimelineRequest sinceId(long sinceId) {
    return parameter("since_id", Long.toString(sinceId));
  }
  
  public List<Status> get() throws TwitterException {
    return JsonUtil.newStatusList(getJson());
  }
}