package net.unto.twitter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

  public final static Status[] newStatusArray(String jsonString)
      throws TwitterException {
    return newStatusArray(JSONArray.fromObject(jsonString));
  }

  public final static Status[] newStatusArray(JSONArray jsonArray)
      throws TwitterException {
    List<Status> statusList = new ArrayList<Status>();
    for (int i = 0; i < jsonArray.size(); i++) {
      statusList.add(newStatus(jsonArray.getJSONObject(i)));
    }
    return statusList.toArray(new Status[statusList.size()]);
  }

  public final static Status newStatus(String jsonString)
      throws TwitterException {
    return newStatus(JSONObject.fromObject(jsonString));
  }

  public final static Status newStatus(JSONObject jsonObject)
      throws TwitterException {
    if (jsonObject == null) {
      return null;
    }
    Status status = new Status();
    if (jsonObject.has("created_at")) {
      status.setCreatedAt(jsonObject.getString("created_at"));
    }
    if (jsonObject.has("id")) {
      status.setId(jsonObject.getString("id"));
    }
    if (jsonObject.has("source")) {
      status.setSource(jsonObject.getString("source"));
    }
    if (jsonObject.has("user")) {
      status.setUser(newUser(jsonObject.getJSONObject("user")));
    }
    if (jsonObject.has("text")) {
      status.setText(jsonObject.getString("text"));
    }

    return status;
  }

  public final static User[] newUserArray(String jsonString)
      throws TwitterException {
    return newUserArray(JSONArray.fromObject(jsonString));
  }

  public final static User[] newUserArray(JSONArray jsonArray)
      throws TwitterException {
    List<User> userList = new ArrayList<User>();
    for (int i = 0; i < jsonArray.size(); i++) {
      userList.add(newUser(jsonArray.getJSONObject(i)));
    }
    return userList.toArray(new User[userList.size()]);
  }

  public final static User newUser(String jsonString) throws TwitterException {
    return newUser(JSONObject.fromObject(jsonString));
  }

  public final static User newUser(JSONObject jsonObject)
      throws TwitterException {
    if (jsonObject == null) {
      return null;
    }
    User user = new User();
    if (jsonObject.has("id")) {
      user.setId(jsonObject.getString("id"));
    }
    if (jsonObject.has("name")) {
      user.setName(jsonObject.getString("name"));
    }
    if (jsonObject.has("location")) {
      user.setLocation(jsonObject.getString("location"));
    }
    if (jsonObject.has("description")) {
      user.setDescription(jsonObject.getString("description"));
    }
    if (jsonObject.has("profile_image_url")) {
      user.setProfileImageUrl(jsonObject.getString("profile_image_url"));
    }
    if (jsonObject.has("url")) {
      user.setUrl(jsonObject.getString("url"));
    }
    if (jsonObject.has("protected")) {
      user.setIsProtected(jsonObject.getBoolean("protected"));
    }
    if (jsonObject.has("friends_count")) {
      user.setFollowingCount(jsonObject.getInt("friends_count"));
    }
    if (jsonObject.has("favorites_count")) {
      user.setFavoritesCount(jsonObject.getInt("favorites_count"));
    }
    if (jsonObject.has("utc_offset")) {
      user.setUtcOffset(jsonObject.getInt("utc_offset"));
    }
    if (jsonObject.has("statuses_count")) {
      user.setStatusesCount(jsonObject.getInt("statuses_count"));
    }
    if (jsonObject.has("followers_count")) {
      user.setFollowersCount(jsonObject.getInt("followers_count"));
    }
    if (jsonObject.has("status")) {
      user.setStatus(newStatus(jsonObject.getJSONObject("status")));
    }
    return user;
  }
}
