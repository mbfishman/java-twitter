package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;
import net.unto.twitter.UtilProtos.Url.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * An implementation of the TwitterHttpManager interface using the Apache
 * Commons HttpClient library.
 * 
 * @author DeWitt Clinton <dewitt@unto.net>
 */
class TwitterHttpManager implements HttpManager {
 
  private static final AuthScope AUTH_SCOPE = new AuthScope("twitter.com", 80,
      AuthScope.ANY_REALM);

  private HttpConnectionManager connectionManager = null;

  private Credentials credentials = null;

  /**
   * Construct a new {@link TwitterHttpManager} instance.
   */
  public TwitterHttpManager(String username, String password) {
    assert username != null;
    assert password != null;
    credentials = new UsernamePasswordCredentials(username, password);
    connectionManager = new MultiThreadedHttpConnectionManager();
  }

  /**
   * Construct a new {@link TwitterHttpManager} instance.
   */
  public TwitterHttpManager() {
    connectionManager = new MultiThreadedHttpConnectionManager();
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see net.unto.twitter.TwitterHttpManager#get(java.lang.String)
   */
  public String get(Url url) throws TwitterException {
    assert url != null;
    GetMethod method = new GetMethod(url.getBaseUrl());
    method.setQueryString(getParametersAsNamedValuePairArray(url));
    method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
    return execute(method);
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.unto.twitter.TwitterHttpManager#post(java.lang.String)
   */
  public String post(Url url) throws TwitterException {
    assert url != null;
    PostMethod method = new PostMethod(url.getBaseUrl());
    method.setQueryString(getParametersAsNamedValuePairArray(url));
    method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
    return execute(method);
  }

  private NameValuePair[] getParametersAsNamedValuePairArray(Url url) {
    List<NameValuePair> out = new ArrayList<NameValuePair>();
    for (Parameter parameter : url.getParametersList()) {
      if (parameter.hasName() && parameter.hasValue()) {
        out.add(new NameValuePair(parameter.getName(), parameter.getValue()
            .toString()));
      }
    }
    return out.toArray(new NameValuePair[out.size()]);
  }

  private String execute(HttpMethod method)
      throws TwitterException {

    HttpClient httpClient = new HttpClient(connectionManager);
    if (credentials != null) {
      httpClient.getState().setCredentials(AUTH_SCOPE, credentials);
      httpClient.getParams().setAuthenticationPreemptive(true);
    } else {
      httpClient.getParams().setAuthenticationPreemptive(false);
    }
    
    try {
      int statusCode = httpClient.executeMethod(method);
      if (statusCode != HttpStatus.SC_OK) {
        String error = String.format("Expected 200 OK. Received %d %s",
            statusCode, HttpStatus.getStatusText(statusCode));
        throw new TwitterException(error);
      }
      String responseBody = method.getResponseBodyAsString();
      if (responseBody == null) {
        throw new TwitterException("Expected response body, got null");
      }
      return responseBody;
    } catch (HttpException e) {
      throw new TwitterException(e);
    } catch (IOException e) {
      throw new TwitterException(e);
    } finally {
      method.releaseConnection();
    }
  }
}
