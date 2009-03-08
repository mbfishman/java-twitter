package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public class UpdateStatusRequest extends AbstractRequest<UpdateStatusRequest> {

  public UpdateStatusRequest(String status) {
    assert(status != null);
    assert(status.length() > 0);
    assert(status.length() <= 160);
    path = "/statuses/update.json";
    authorizationRequired = true;
    // TODO(dewitt): Verify URL encoding
    parameter("status", status);
  }

  public UpdateStatusRequest inReplyToStatusId(long inReplyToStatusId) {
    return parameter("in_reply_to_status_id", Long.toString(inReplyToStatusId));
  }
  
  public Status post() throws TwitterException {
    return JsonUtil.newStatus(postJson());
  }
}
