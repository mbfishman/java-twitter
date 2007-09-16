package net.unto.twitter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;

public class DirectMessage
{
  private String senderScreenName;
  
  private Long recipientId;
  
  private User sender;
  
  private DateTime createdAt;
  
  private String recipientScreenName;
  
  private User recipient;
  
  private String text;
  
  private Long senderId;
  
  private Long id;

  public String getSenderScreenName( )
  {
    return senderScreenName;
  }

  public void setSenderScreenName( String senderScreenName )
  {
    this.senderScreenName = senderScreenName;
  }
  
  public boolean hasSenderScreenName( )
  {
    return senderScreenName != null;
  }

  public Long getRecipientId( )
  {
    return recipientId;
  }

  public void setRecipientId( Long recipientId )
  {
    this.recipientId = recipientId;
  }

  public boolean hasRecipientId( )
  {
    return recipientId != null;
  }
  
  public User getSender( )
  {
    return sender;
  }

  public void setSender( User sender )
  {
    this.sender = sender;
  }

  public boolean hasSender( )
  {
    return sender != null;
  }
  
  public DateTime getCreatedAt( )
  {
    return createdAt;
  }

  public void setCreatedAt( DateTime createdAt )
  {
    this.createdAt = createdAt;
  }

  public void setCreatedAt(String createdAtString) {
    setCreatedAt(TwitterUtil.parseTwitterDateTimeString(createdAtString));
  }
  
  public boolean hasCreatedAt( )
  {
    return createdAt != null;
  }
  
  public String getRecipientScreenName( )
  {
    return recipientScreenName;
  }

  public void setRecipientScreenName( String recipientScreenName )
  {
    this.recipientScreenName = recipientScreenName;
  }

  public boolean hasRecipientScreenName( )
  {
    return recipientScreenName != null;
  }
  
  public User getRecipient( )
  {
    return recipient;
  }

  public void setRecipient( User recipient )
  {
    this.recipient = recipient;
  }

  public boolean hasRecipient( )
  {
    return recipient != null;
  }
  
  public String getText( )
  {
    return text;
  }

  public void setText( String text )
  {
    this.text = text;
  }

  public boolean hasText( )
  {
    return text != null;
  }
  
  public Long getSenderId( )
  {
    return senderId;
  }

  public void setSenderId( Long senderId )
  {
    this.senderId = senderId;
  }

  public boolean hasSenderId( )
  {
    return senderId != null;
  }
  
  public Long getId( )
  {
    return id;
  }

  public void setId( Long id )
  {
    this.id = id;
  }
  
  public boolean hasId( )
  {
    return id != null;
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
