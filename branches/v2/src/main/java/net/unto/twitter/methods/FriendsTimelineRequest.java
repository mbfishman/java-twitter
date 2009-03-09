package net.unto.twitter.methods;

import java.util.List;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterProtos.Status;

import org.joda.time.DateTime;

public final class FriendsTimelineRequest extends AbstractRequest {
  
  public static Builder builder() {
    return new Builder();
  }
  
  FriendsTimelineRequest(Builder builder) throws TwitterException {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder() {
      path("/statuses/friends_timeline.json");
      authorizationRequired(true);
    }
    
    public FriendsTimelineRequest build() throws TwitterException {
      return new FriendsTimelineRequest(this);
    }
    
    public Builder since(DateTime since) {
      assert (since != null);
      return parameter("since", since.toString());
    }

    public Builder sinceId(long sinceId) {
      return parameter("since_id", Long.toString(sinceId));
    }

    public Builder count(int count) {
      return parameter("count", Integer.toString(count));
    }

    public Builder page(int page) {
      return parameter("page", Integer.toString(page));
    }
    
  }
  
  public List<Status> get() throws TwitterException {
    return JsonUtil.newStatusList(getJson());
  }
}

