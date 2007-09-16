package net.unto.twitter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class User {

  public User() {
  }

  private Integer utcOffset;

  public boolean hasUtcOffset() {
    return utcOffset != null;
  }
  
  public Integer getUtcOffset() {
    return utcOffset;
  }

  public void setUtcOffset(Integer utcOffset) {
    this.utcOffset = utcOffset;
  }
  
  private Integer statusesCount;

  public boolean hasStatusesCount() {
    return statusesCount != null;
  }
  
  public Integer getStatusesCount() {
    return statusesCount;
  }

  public void setStatusesCount(Integer statusesCount) {
    this.statusesCount = statusesCount;
  }
  
  private Integer followingCount;

  public boolean hasFollowingCount() {
    return followingCount != null;
  }
  
  public Integer getFollowingCount() {
    return followingCount;
  }

  public void setFollowingCount(Integer followingCount) {
    this.followingCount = followingCount;
  }

  private String url;

  public boolean hasUrl() {
    return url != null;
  }
  
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  private Integer followersCount;
  
  public boolean hasFollowersCount() {
    return followersCount != null;
  }

  public Integer getFollowersCount() {
    return followersCount;
  }

  public void setFollowersCount(Integer followersCount) {
    this.followersCount = followersCount;
  }

  private Integer favoritesCount;
  
  public boolean hasFavoritesCount() {
    return favoritesCount != null;
  }
  
  public Integer getFavoritesCount() {
    return favoritesCount;
  }

  public void setFavoritesCount(Integer favoritesCount) {
    this.favoritesCount = favoritesCount;
  }

  private Boolean isProtected;
  
  public boolean hasProtected() {
    return isProtected != null;
  }
  
  public Boolean getIsProtected() {
    return isProtected;
  }

  public void setIsProtected(Boolean isProtected) {
    this.isProtected = isProtected;
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

  private Long id = null;

  public boolean hasId() {
    return id != null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
