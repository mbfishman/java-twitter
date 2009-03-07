package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public class DestroyStatusRequest extends AbstractRequest<DestroyStatusRequest> {

  public DestroyStatusRequest(long id) {
    path = String.format("/statuses/destroy/%s.json", id);
  }

  public Status post() throws TwitterException {
    return JsonUtil.newStatus(postJson());
  }
}
