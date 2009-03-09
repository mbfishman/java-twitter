package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public final class ShowStatusRequest extends AbstractRequest {
  
  public static Builder builder(long id) {
    return new Builder(id);
  }
  
  ShowStatusRequest(Builder builder) throws TwitterException {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder(long id) {
      path(String.format("/statuses/show/%s.json", id));
      authorizationRequired(true);
    }
    
    public ShowStatusRequest build() throws TwitterException {
      return new ShowStatusRequest(this);
    }
  }

  public Status get() throws TwitterException {
    return JsonUtil.newStatus(getJson());
  }
}
