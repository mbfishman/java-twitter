package net.unto.twitter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Status {

 /*  {
    "id": 218838062,
    "source": "web",
    "created_at": "Tue Aug 21 22:09:01 +0000 2007",
    "user": {
     "screen_name": "dewitt",
     "url": "http:\/\/unto.net\/",
     "description": "Indeterminate things",
     "profile_image_url": "http:\/\/assets0.twitter.com\/system\/user\/profile_image\/673483\/normal\/me.jpg?1171965914",
     "protected": false,
     "location": "San Francisco, CA",
     "id": 673483,
     "name": "DeWitt"
    },
    "text": "A few months late, I finally bought a copy of Dungen's _Tio Bitar_.  Only 3 minutes into it and sure enough, they're still the best."
   }*/

  public Status() {
  }

  public Status(String id, String source, Date createdAt, User user, 
      String text) {
    setId(id);
    setSource(source);
    setCreatedAt(createdAt);
    setUser(user);
    setText(text);
  }

  private String id;

  public boolean hasId() {
    return id != null;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  private String source;
  
  public boolean hasSource() {
    return source != null;
  }
  
  public String getSource() {
    return source;
  }
  
  public void setSource(String source) {
    this.source = source;
  }
  
  private Date createdAt;

  public boolean hasCreatedAt() {
    return createdAt != null;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setCreatedAt(String createdAtString) throws ParseException {
    setCreatedAt(getTwitterDateFormat().parse(createdAtString));
  }

  public String getRelativeCreatedAt() {
    // TODO(dewitt): Create relative_created_at string
    return null;
  }

  private User user;

  public boolean hasUser() {
    return user != null;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  
  private String text;

  public boolean hasText() {
    return text != null;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  protected static DateFormat getTwitterDateFormat() {
    // Dates are in the form: "Fri Mar 09 18:59:02 +0000 2007"
    // As date formats instances are not synchronized, created a new one
    return new SimpleDateFormat("EEE MMM d H:m:s Z yyyy");
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", getId())
        .append("source", getSource())
        .append("created_at", getCreatedAt())
        .append("relative_created_at", getRelativeCreatedAt())
        .append("user", hasUser() ? getUser().getId() : null)
        .append("text", getText()).toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (!(obj instanceof Status))) {
      return false;
    }
    Status status = (Status) obj;
    return new EqualsBuilder()
        .append(getId(), status.getId())
        .append(getSource(), status.getSource())
        .append(getCreatedAt(), status.getCreatedAt())
        .append(getUser(), status.getUser())
        .append(getText(), status.getText()).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getSource())
        .append(getCreatedAt())
        .append(getUser())
        .append(getText()).toHashCode();
  }

}
