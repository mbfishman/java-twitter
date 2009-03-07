package net.unto.twitter;

import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.UtilProtos.Url;

public class UpdateStatusRequest extends AbstractRequest {

  private String baseUrl = "http://twitter.com/statuses/update.json";

  UpdateStatusRequest(HttpManager httpManager, String status) {
    this.httpManager = httpManager;
    assert(status != null);
    assert(status.length() > 0);
    assert(status.length() <= 160);
    // TODO(dewitt): Verify URL encoding
    AddParameter("status", status);
  }

  public UpdateStatusRequest inReplyToStatusId(long inReplyToStatusId) {
    AddParameter("in_reply_to_status_id", Long.toString(inReplyToStatusId));
    return this;
  }
  
  public Status get() throws TwitterException {
    Url url = Url.newBuilder().setBaseUrl(baseUrl).addAllParameters(parameters)
        .build();
    return JsonUtil.newStatus(httpManager.post(url));
  }
}
