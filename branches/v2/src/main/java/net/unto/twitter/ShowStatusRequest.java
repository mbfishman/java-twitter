package net.unto.twitter;

import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.UtilProtos.Url;

public class ShowStatusRequest extends AbstractRequest {

  private String baseUrl = null;

  ShowStatusRequest(HttpManager httpManager, long id) {
    this.httpManager = httpManager;
    baseUrl = 
      String.format("http://twitter.com/statuses/show/%s.json", id);
  }

  public Status get() throws TwitterException {
    Url url = Url.newBuilder().setBaseUrl(baseUrl).addAllParameters(parameters)
        .build();
    return JsonUtil.newStatus(httpManager.get(url));
  }
}
