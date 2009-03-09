package net.unto.twitter.methods;

import java.util.List;

import org.joda.time.DateTime;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterProtos.Status;

public final class RepliesRequest extends AbstractRequest {
  
  public static Builder builder() {
    return new Builder();
  }
  
  RepliesRequest(Builder builder) {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder() {
      path("/statuses/replies.json");
      authorizationRequired(true);
    }
    
    public RepliesRequest build() {
      return new RepliesRequest(this);
    }

    public Builder since(DateTime since) {
      assert (since != null);
      return parameter("since", since.toString());
    }

    public Builder sinceId(long sinceId) {
      return parameter("since_id", Long.toString(sinceId));
    }

    public Builder page(int page) {
      return parameter("page", Integer.toString(page));
    }    
  }
  
  public List<Status> get() {
    return JsonUtil.newStatusList(getJson());
  }
}

