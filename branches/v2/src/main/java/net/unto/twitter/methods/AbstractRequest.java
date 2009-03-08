package net.unto.twitter.methods;

import java.util.ArrayList;
import java.util.List;

import net.unto.twitter.Api;
import net.unto.twitter.HttpManager;
import net.unto.twitter.TwitterException;
import net.unto.twitter.TwitterHttpManager;
import net.unto.twitter.UtilProtos.Url;


@SuppressWarnings("unchecked")  
abstract class AbstractRequest<RequestType extends AbstractRequest> {

  HttpManager httpManager = null;

  List<Url.Parameter> parameters = new ArrayList<Url.Parameter>();

  String path = null;

  private Url baseUrl = Api.DEFAULT_BASE_URL;
  
  boolean authorizationRequired = false;
  
  public RequestType httpManager(HttpManager httpManager) {
    this.httpManager = httpManager;
    return (RequestType)this;  // Safe conversion because RequestType extends AbstractRequest
  }
  
  public RequestType baseUrl(Url baseUrl) {
    this.baseUrl = baseUrl;
    return (RequestType)this;
  }
  
  HttpManager getHttpManager() {
    return httpManager != null ?
        httpManager :
        TwitterHttpManager.builder().build();
  }
  
  Url buildUrl() {
    assert(baseUrl != null);
    assert(path != null);
    return Url.newBuilder()
        .setScheme(baseUrl.getScheme())
        .setHost(baseUrl.getHost())
        .setPort(baseUrl.getPort())
        .setPath(path)
        .addAllParameters(parameters)
        .build();
  }
  
  RequestType parameter(String name, String value) {
    assert(name != null);
    assert(name.length() > 0);
    assert(value != null);
    parameters.add(
        Url.Parameter.newBuilder()
            .setName(name)
            .setValue(value)
            .build());
    return (RequestType)this;
  }
  
  void requireCredentials() throws TwitterException {
    if (!getHttpManager().hasCredentials()) {
      throw new TwitterException("Authorization required.");
    }
  }
  
  String getJson() throws TwitterException {
    if (authorizationRequired) {
      requireCredentials();
    }
    return getHttpManager().get(buildUrl());
  }
  
  String postJson() throws TwitterException {
    if (authorizationRequired) {
      requireCredentials();
    }
   return getHttpManager().post(buildUrl());
  }
}
