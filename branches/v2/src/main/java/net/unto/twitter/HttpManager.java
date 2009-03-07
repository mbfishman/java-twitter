package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;

/**
 * A simple HTTP connection interface.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
public interface HttpManager
{
  boolean hasCredentials();
  
  /**
   * Perform an HTTP GET request to the specified URL.
   * 
   * @param url the {@link Url} to HTTP GET.
   * @return a String containing the body of the HTTP GET response.
   * @throws TwitterException
   */
  String get(Url url) throws TwitterException;
  
  /**
   * Perform an HTTP POST request to the specified URL.
   * 
   * @param url the {@link Url} to HTTP POST.
   * @return a String containing the body of the HTTP POST response.
   * @throws TwitterException
   */
  String post(Url url) throws TwitterException;
}
