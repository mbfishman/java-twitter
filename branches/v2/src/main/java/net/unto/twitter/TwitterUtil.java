package net.unto.twitter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class TwitterUtil {
  public final static DateTimeFormatter TWITTER_DATE_FORMATTER = DateTimeFormat
      .forPattern("EEE MMM dd HH:mm:ss Z yyyy");

  public final static DateTime parseTwitterDateTimeString(String twitterDateString) {
    try {
      return TWITTER_DATE_FORMATTER.parseDateTime(twitterDateString);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException(String.format("Could not parse date string '%s'",
          twitterDateString));
    }
  }
  
  public final static String toString(DateTime dateTime) {
    return dateTime.toString(TWITTER_DATE_FORMATTER);
  }
}
