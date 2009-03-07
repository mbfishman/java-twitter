package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public class ShowStatusRequest extends AbstractRequest<ShowStatusRequest> {

  public ShowStatusRequest(long id) {
    path = String.format("/statuses/show/%s.json", id);
  }

  public Status get() throws TwitterException {
    return JsonUtil.newStatus(getJson());
  }
}
