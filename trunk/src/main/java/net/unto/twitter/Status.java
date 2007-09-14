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
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
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
