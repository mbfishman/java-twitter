package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

import org.joda.time.DateTime;

public class RepliesRequest extends AbstractRequest<RepliesRequest> {

  public RepliesRequest() {
    path = "/statuses/replies.json";
  }
  
  public RepliesRequest since(DateTime since) {
    assert (since != null);
    return parameter("since", since.toString());
  }

  public RepliesRequest sinceId(long sinceId) {
    return parameter("since_id", Long.toString(sinceId));
  }

  public RepliesRequest page(int page) {
    return parameter("page", Integer.toString(page));
  }

  public List<Status> get() throws TwitterException {
    // TODO(dewitt): Must be authenticated
    return JsonUtil.newStatusList(getJson());
  }
}
