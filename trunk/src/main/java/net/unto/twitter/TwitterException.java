package net.unto.twitter;

public class TwitterException extends Exception {

  private static final long serialVersionUID = -7004865779218982263L;

  public TwitterException(String string) {
    super(string);
  }

  public TwitterException(Exception e) {
    super(e);
  }

}
