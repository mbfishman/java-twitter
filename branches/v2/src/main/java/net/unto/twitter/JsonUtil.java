package net.unto.twitter;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.unto.twitter.TwitterProtos.Trends;
import net.unto.twitter.TwitterProtos.User;
import net.unto.twitter.TwitterProtos.Status;
import net.unto.twitter.TwitterProtos.DirectMessage;
import net.unto.twitter.TwitterProtos.Trends.Trend;

public class JsonUtil {
  
  private JsonUtil() {
  }
  
  private final static User newUser(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    User.Builder builder = User.newBuilder();
    if (jsonObject.has("id")) {
      builder.setId(jsonObject.getLong("id"));
    }
    if (jsonObject.has("name")) {
      builder.setName(jsonObject.getString("name"));
    }
    if (jsonObject.has("screen_name")) {
      builder.setScreenName(jsonObject.getString("screen_name"));
    }
    if (jsonObject.has("location")) {
      builder.setLocation(jsonObject.getString("location"));
    }
    if (jsonObject.has("description")) {
      builder.setDescription(jsonObject.getString("description"));
    }
    builder.setProfile(newUserProfile(jsonObject));
    if (jsonObject.has("url")) {
      builder.setUrl(jsonObject.getString("url"));
    }
    if (jsonObject.has("protected")) {
      builder.setProtected(jsonObject.getBoolean("protected"));
    }
    if (jsonObject.has("followers_count")) {
      builder.setFollowersCount(jsonObject.getInt("followers_count"));
    }
    if (jsonObject.has("friends_count")) {
      builder.setFriendsCount(jsonObject.getInt("friends_count"));
    }
    if (jsonObject.has("created_at")) {
      builder.setCreatedAt(jsonObject.getString("created_at"));
    }
    if (jsonObject.has("favorites_count")) {
      builder.setFavoritesCount(jsonObject.getInt("favorites_count"));
    }
    if (jsonObject.has("utc_offset")) {
      builder.setUtcOffset(jsonObject.getInt("utc_offset"));
    }
    if (jsonObject.has("time_zone")) {
      builder.setTimeZone(jsonObject.getString("time_zone"));
    }
    if (jsonObject.has("following")) {
      builder.setFollowing(jsonObject.getBoolean("following"));
    }
    if (jsonObject.has("notifications")) {
      builder.setNotifications(jsonObject.getBoolean("notifications"));
    }
    if (jsonObject.has("statuses_count")) {
      builder.setStatusesCount(jsonObject.getInt("statuses_count"));
    }
    if (jsonObject.has("status")) {
      builder.setStatus(newStatus(jsonObject.getJSONObject("status")));
    }
    return builder.build();
  }

  public final static List<User> newUserList(String jsonString) {
    return newUserList(JSONArray.fromObject(jsonString));
  }

  private final static List<User> newUserList(JSONArray jsonArray) {
    List<User> users = new ArrayList<User>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      users.add(newUser(jsonArray.getJSONObject(i)));
    }
    return users;
  }

  public final static User newUser(String jsonString) {
    return newUser(JSONObject.fromObject(jsonString));
  }

  private final static User.Profile newUserProfile(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    User.Profile.Builder builder = User.Profile.newBuilder();
    if (jsonObject.has("profile_image_url")) {
      builder.setImageUrl(jsonObject.getString("profile_image_url"));
    }
    if (jsonObject.has("profile_background_color")) {
      builder.setBackgroundColor(jsonObject.getString("profile_background_color"));
    }
    if (jsonObject.has("profile_text_color")) {
      builder.setTextColor(jsonObject.getString("profile_text_color"));
    }
    if (jsonObject.has("profile_link_color")) {
      builder.setLinkColor(jsonObject.getString("profile_link_color"));
    }
    if (jsonObject.has("profile_sidebar_fill_color")) {
      builder.setSidebarFillColor(jsonObject.getString("profile_sidebar_fill_color"));
    }
    if (jsonObject.has("profile_sidebar_border_color")) {
      builder.setSidebarBorderColor(jsonObject.getString("profile_sidebar_border_color"));
    }
    return builder.build();
  }
  
  public final static Status newStatus(String jsonString) {
    return newStatus(JSONObject.fromObject(jsonString));
  }
  
  private final static Status newStatus(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    Status.Builder builder = Status.newBuilder();
    
    if (jsonObject.has("created_at")) {
      builder.setCreatedAt(jsonObject.getString("created_at"));
    }
    if (jsonObject.has("id")) {
      builder.setId(jsonObject.getLong("id"));
    }
    if (jsonObject.has("text")) {
      builder.setText(jsonObject.getString("text"));
    }
    if (jsonObject.has("source")) {
      builder.setSource(jsonObject.getString("source"));
    }
    if (jsonObject.has("truncated")) {
      builder.setTruncated(jsonObject.getBoolean("truncated"));
    }
    if (jsonObject.has("in_reply_to_status_id")) {
      builder.setInReplyToStatusId(jsonObject.getLong("in_reply_to_status_id"));
    }
    if (jsonObject.has("in_reply_to_user_id")) {
      builder.setInReplyToUserId(jsonObject.getLong("in_reply_to_user_id"));
    }
    if (jsonObject.has("user")) {
      builder.setUser(newUser(jsonObject.getJSONObject("user")));
    }
    if (jsonObject.has("favorited")) {
      builder.setFavorited(jsonObject.getBoolean("favorited"));
    }
    return builder.build();
  }
  
  public final static List<Status> newStatusList(String jsonString) {
    return newStatusList(JSONArray.fromObject(jsonString));
  }

  private final static List<Status> newStatusList(JSONArray jsonArray) {
    List<Status> statuses = new ArrayList<Status>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      statuses.add(newStatus(jsonArray.getJSONObject(i)));
    }
    return statuses;
  }
  
  private final static DirectMessage newDirectMessage(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    DirectMessage.Builder builder = DirectMessage.newBuilder();
    
    if (jsonObject.has("id")) {
      builder.setId(jsonObject.getLong("id"));
    }
    if (jsonObject.has("text")) {
      builder.setText(jsonObject.getString("text"));
    }
    if (jsonObject.has("sender_id")) {
      builder.setSenderId(jsonObject.getLong("sender_id"));
    }
    if (jsonObject.has("recipient_id")) {
      builder.setRecipientId(jsonObject.getLong("recipient_id"));
    }
    if (jsonObject.has("created_at")) {
      builder.setCreatedAt(jsonObject.getString("created_at"));
    }
    if (jsonObject.has("sender_screen_name")) {
      builder.setSenderScreenName(jsonObject.getString("sender_screen_name"));
    }
    if (jsonObject.has("recipient_screen_name")) {
      builder.setRecipientScreenName(jsonObject.getString("recipient_screen_name"));
    }
    if (jsonObject.has("sender")) {
      builder.setSender(newUser(jsonObject.getJSONObject("sender")));
    }
    if (jsonObject.has("recipient")) {
      builder.setRecipient(newUser(jsonObject.getJSONObject("recipient")));
    }
    return builder.build();
  }
  
  public final static List<DirectMessage> newDirectMessageList(String jsonString) {
    return newDirectMessageList(JSONArray.fromObject(jsonString));
  }

  private final static List<DirectMessage> newDirectMessageList(JSONArray jsonArray) {
    List<DirectMessage> directMessages = new ArrayList<DirectMessage>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      directMessages.add(newDirectMessage(jsonArray.getJSONObject(i)));
    }
    return directMessages;
  }

  public final static DirectMessage newDirectMessage(String jsonString)  {
    return newDirectMessage(JSONObject.fromObject(jsonString));
  }
  
  public final static long[] newLongArray(String jsonString) {
    return newLongArray(JSONArray.fromObject(jsonString));
  }
  
  private final static long[] newLongArray(JSONArray jsonArray) {
    long[] longs = new long[jsonArray.size()];
    for (int i = 0; i < jsonArray.size(); i++) {
      longs[i] = jsonArray.getLong(i);
    }
    return longs;
  }
  
  public final static Trends newTrends(String jsonString) {
    return newTrends(JSONObject.fromObject(jsonString));
  }
  
  private final static Trends newTrends(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    Trends.Builder builder = Trends.newBuilder();
    if (jsonObject.has("as_of")) {
      builder.setAsOf(jsonObject.getString("as_of"));
    }
    if (jsonObject.has("trends")) {
      JSONArray trendsJsonArray = jsonObject.getJSONArray("trends");
      for (int i = 0; i < trendsJsonArray.size(); i++) {
        builder.addTrends(newTrend(trendsJsonArray.getJSONObject(i)));
      }
    }
    return builder.build();
  }

  private final static Trend newTrend(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    Trends.Trend.Builder builder = Trends.Trend.newBuilder();
    if (jsonObject.has("name")) {
      builder.setName(jsonObject.getString("name"));
    }
    if (jsonObject.has("url")) {
      builder.setUrl(jsonObject.getString("url"));
    }
    return builder.build();
  }

}