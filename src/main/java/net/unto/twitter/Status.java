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

  public Status() {
  }

  public Status(Date createdAt, Long id, String relativeCreatedAt, String text,
      User user) {
    setCreatedAt(createdAt);
    setId(id);
    setRelativeCreatedAt(relativeCreatedAt);
    setText(text);
    setUser(user);
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

  private Long id;

  public boolean hasId() {
    return id != null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  private String relativeCreatedAt;

  public boolean hasRelativeCreatedAt() {
    return relativeCreatedAt != null;
  }

  public String getRelativeCreatedAt() {
    return relativeCreatedAt;
  }

  public void setRelativeCreatedAt(String relativeCreatedAt) {
    this.relativeCreatedAt = relativeCreatedAt;
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

  protected static DateFormat getTwitterDateFormat() {
    // Dates are in the form: "Fri Mar 09 18:59:02 +0000 2007"
    // As date formats instances are not synchronized, created a new one
    return new SimpleDateFormat("EEE MMM d H:m:s Z yyyy");
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(
        "created_at", getCreatedAt()).append("id", getId()).append(
        "relative_created_at", getRelativeCreatedAt())
        .append("text", getText()).append("user",
            hasUser() ? getUser().getId() : null).toString();
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
    return new EqualsBuilder().append(getCreatedAt(), status.getCreatedAt())
        .append(getId(), status.getId()).append(getRelativeCreatedAt(),
            status.getRelativeCreatedAt()).append(getText(), status.getText())
        .append(getUser(), status.getUser()).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getCreatedAt()).append(getId())
        .append(getRelativeCreatedAt()).append(getText()).append(getUser())
        .toHashCode();
  }
}
