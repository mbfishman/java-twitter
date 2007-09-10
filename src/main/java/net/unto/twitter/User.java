package net.unto.twitter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class User {

  /*  {
    "status": {
     "text": "A few months late, I finally bought a copy of Dungen's _Tio Bitar_.  Only 3 minutes into it and sure enough, they're still the best.",
     "created_at": "Tue Aug 21 22:09:01 +0000 2007",
     "id": 218838062
    },
    "utc_offset": -28800,
    "statuses_count": 76,
    "name": "DeWitt",
    "friends_count": 42,
    "url": "http:\/\/unto.net\/",
    "profile_sidebar_fill_color": "CCCCCC",
    "profile_link_color": "666666",
    "profile_image_url": "http:\/\/assets0.twitter.com\/system\/user\/profile_image\/673483\/normal\/me.jpg?1171965914",
    "screen_name": "dewitt",
    "profile_text_color": "121212",
    "followers_count": 96,
    "profile_sidebar_border_color": "333333",
    "location": "San Francisco, CA",
    "profile_background_color": "FFFFFF",
    "favourites_count": 2,
    "protected": false,
    "id": 673483,
    "description": "Indeterminate things"
   }*/

  public User() {
  }

  public User(String screenName, String url, String description, String profileImageUrl,
      boolean isProtected, String location, String id, String name, Status status) {
    setDescription(description);
    setId(id);
    setLocation(location);
    setName(name);
    setProfileImageUrl(profileImageUrl);
    setScreenName(screenName);
    setStatus(status);
  }

  private String description;

  public boolean hasDescription() {
    return description != null;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private String id = null;

  public boolean hasId() {
    return id != null;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private String location = null;

  public boolean hasLocation() {
    return location != null;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  private String name = null;

  public boolean hasName() {
    return name != null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String profileImageUrl = null;

  public boolean hasProfileImageUrl() {
    return profileImageUrl != null;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  private String screenName = null;

  public boolean hasScreenName() {
    return screenName != null;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  private Status status = null;

  public boolean hasStatus() {
    return status != null;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(
        "description", getDescription()).append("id", getId()).append(
        "location", getLocation()).append("name", getName()).append(
        "profile_image_url", getProfileImageUrl()).append("screen_name",
        getScreenName()).append("status",
        hasStatus() ? getStatus().getId() : null).toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (!(obj instanceof User))) {
      return false;
    }
    User user = (User) obj;
    return new EqualsBuilder().append(getDescription(), user.getDescription())
        .append(getId(), user.getId())
        .append(getLocation(), user.getLocation()).append(getName(),
            user.getName()).append(getProfileImageUrl(),
            user.getProfileImageUrl()).append(getScreenName(),
            user.getScreenName()).append(getStatus(), user.getStatus())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getDescription()).append(getId())
        .append(getLocation()).append(getName()).append(getProfileImageUrl())
        .append(getScreenName()).append(getStatus()).toHashCode();
  }
}
