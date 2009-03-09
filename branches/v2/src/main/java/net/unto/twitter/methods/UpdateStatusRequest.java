package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

public final class UpdateStatusRequest extends AbstractRequest {
  
  public static Builder builder(String status) {
    return new Builder(status);
  }
  
  UpdateStatusRequest(Builder builder) throws TwitterException {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder(String status) {
      assert(status != null);
      assert(status.length() > 0);
      assert(status.length() <= 160);
      path("/statuses/update.json");
      parameter("status", status);
      authorizationRequired(true);
    }
    
    public UpdateStatusRequest build() throws TwitterException {
      return new UpdateStatusRequest(this);
    }
  }

  public Status post() throws TwitterException {
    return JsonUtil.newStatus(postJson());
  }
}


