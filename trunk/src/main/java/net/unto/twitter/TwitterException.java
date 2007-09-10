package net.unto.twitter;

public class TwitterException extends Exception {

  public TwitterException(String string) {
    super(string);
  }

  public TwitterException(Exception e) {
    super(e);
  }

}
