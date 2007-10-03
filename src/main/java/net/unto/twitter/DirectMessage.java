package net.unto.twitter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class DirectMessage {
  private String senderScreenName;

  private Long recipientId;

  private User sender;

  private DateTime createdAt;

  private String recipientScreenName;

  private User recipient;

  private String text;

  private Long senderId;

  private Long id;

  public String getSenderScreenName() {
    return senderScreenName;
  }

  public void setSenderScreenName(String senderScreenName) {
    this.senderScreenName = senderScreenName;
  }

  public boolean hasSenderScreenName() {
    return senderScreenName != null;
  }

  public Long getRecipientId() {
    return recipientId;
  }

  public void setRecipientId(Long recipientId) {
    this.recipientId = recipientId;
  }

  public boolean hasRecipientId() {
    return recipientId != null;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public boolean hasSender() {
    return sender != null;
  }

  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setCreatedAt(String createdAtString) {
    setCreatedAt(TwitterUtil.parseTwitterDateTimeString(createdAtString));
  }

  public boolean hasCreatedAt() {
    return createdAt != null;
  }

  public String getRecipientScreenName() {
    return recipientScreenName;
  }

  public void setRecipientScreenName(String recipientScreenName) {
    this.recipientScreenName = recipientScreenName;
  }

  public boolean hasRecipientScreenName() {
    return recipientScreenName != null;
  }

  public User getRecipient() {
    return recipient;
  }

  public void setRecipient(User recipient) {
    this.recipient = recipient;
  }

  public boolean hasRecipient() {
    return recipient != null;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean hasText() {
    return text != null;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public boolean hasSenderId() {
    return senderId != null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean hasId() {
    return id != null;
  }

  public final static DirectMessage[] newArrayFromJsonString(String jsonString)
      throws TwitterException {
    return newArrayFromJsonArray(JSONArray.fromObject(jsonString));
  }

  public final static DirectMessage[] newArrayFromJsonArray(JSONArray jsonArray)
      throws TwitterException {
    List<DirectMessage> directMessageList = new ArrayList<DirectMessage>();
    for (int i = 0; i < jsonArray.size(); i++) {
      directMessageList.add(newFromJsonObject(jsonArray.getJSONObject(i)));
    }
    return directMessageList
        .toArray(new DirectMessage[directMessageList.size()]);
  }

  public final static DirectMessage newFromJsonString(String jsonString)
      throws TwitterException {
    return newFromJsonObject(JSONObject.fromObject(jsonString));
  }

  public final static DirectMessage newFromJsonObject(JSONObject jsonObject)
      throws TwitterException {
    if (jsonObject == null) {
      return null;
    }
    DirectMessage directMessage = new DirectMessage();
    if (jsonObject.has("sender_screen_name")) {
      directMessage.setSenderScreenName(jsonObject
          .getString("sender_screen_name"));
    }
    if (jsonObject.has("recipient_id")) {
      directMessage.setRecipientId(jsonObject.getLong("recipient_id"));
    }
    if (jsonObject.has("sender")) {
      directMessage.setSender(User.newFromJsonObject(jsonObject
          .getJSONObject("sender")));
    }
    if (jsonObject.has("created_at")) {
      directMessage.setCreatedAt(jsonObject.getString("created_at"));
    }
    if (jsonObject.has("recipient_screen_name")) {
      directMessage.setRecipientScreenName(jsonObject
          .getString("recipient_screen_name"));
    }
    if (jsonObject.has("recipient")) {
      directMessage.setRecipient(User.newFromJsonObject(jsonObject
          .getJSONObject("recipient")));
    }
    if (jsonObject.has("text")) {
      directMessage.setText(jsonObject.getString("text"));
    }
    if (jsonObject.has("sender_id")) {
      directMessage.setSenderId(jsonObject.getLong("sender_id"));
    }
    if (jsonObject.has("id")) {
      directMessage.setId(jsonObject.getLong("id"));
    }
    return directMessage;
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
