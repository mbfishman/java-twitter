package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public final class DestroyStatusRequest extends AbstractRequest {
  
  public static Builder builder(long id) {
    return new Builder(id);
  }
  
  DestroyStatusRequest(Builder builder) throws TwitterException {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder(long id) {
      path(String.format("/statuses/destroy/%s.json", id));
      authorizationRequired(true);
    }
    
    public DestroyStatusRequest build() throws TwitterException {
      return new DestroyStatusRequest(this);
    }
  }

  public Status post() throws TwitterException {
    return JsonUtil.newStatus(postJson());
  }
}
