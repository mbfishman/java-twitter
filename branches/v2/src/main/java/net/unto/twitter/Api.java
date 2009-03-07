package net.unto.twitter;

import java.util.List;

import net.unto.twitter.TwitterProtos.User;
import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.TwitterProtos.DirectMessage;
import net.unto.twitter.UtilProtos.Url;

import org.joda.time.DateTime;

/**
 * Instances of the Api class provide access to the Twitter web service.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
public class Api {

  public static class Builder {
    
    public Api build() throws TwitterException {
      if ((username != null) && (password == null)) {
        throw new TwitterException("Password must be set if username is set.");
      }
      if ((password != null) && (username == null)) {
        throw new TwitterException("Username must be set if password is set.");
      }
      if ((username != null) && (httpManager != null)) {
        throw new TwitterException("Only one of httpManager and username can be set.");
      }
      return new Api(this);
    }

    private HttpManager httpManager = null;
    private String username = null;
    private String password = null;

    public Builder httpManager(HttpManager httpManager) {
      this.httpManager = httpManager;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  private Api(Builder builder) {
    if (builder.httpManager != null) {
      this.httpManager = builder.httpManager;
    } else if (builder.username != null) {
      this.httpManager = new TwitterHttpManager(builder.username, builder.password);
    } else {
      this.httpManager = new TwitterHttpManager();
    }
  }

  private HttpManager httpManager = null;
  
  public PublicTimelineRequest PublicTimeline() {
    return new PublicTimelineRequest(httpManager);
  }

  public FriendsTimelineRequest FriendsTimeline() {
    return new FriendsTimelineRequest(httpManager);
  }
  
  public UserTimelineRequest UserTimeline() {
    return new UserTimelineRequest(httpManager);
  }

  public ShowStatusRequest ShowStatus(long id) {
    return new ShowStatusRequest(httpManager, id);
  }

  public UpdateStatusRequest UpdateStatus(String status) {
    return new UpdateStatusRequest(httpManager, status);
  }


}
