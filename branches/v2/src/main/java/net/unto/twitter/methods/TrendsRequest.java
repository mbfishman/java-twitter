package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterProtos.Trends;

public final class TrendsRequest extends AbstractRequest {
  
  public static Builder builder() {
    return new Builder();
  }
  
  TrendsRequest(Builder builder) {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder() {
      host("search.twitter.com");
      path("/trends.json");
      authorizationRequired(false);
    }
    
    public TrendsRequest build() {
      return new TrendsRequest(this);
    }
  }
  
  public Trends get() {
    return JsonUtil.newTrends(getJson());
  }
}

