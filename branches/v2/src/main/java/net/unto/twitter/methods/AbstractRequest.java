package net.unto.twitter.methods;

import java.util.ArrayList;
import java.util.List;

import net.unto.twitter.Api;
import net.unto.twitter.HttpManager;
import net.unto.twitter.UrlUtil;
import net.unto.twitter.UtilProtos.Url;

abstract class AbstractRequest implements Request {

  HttpManager httpManager;

  Url url;

  AbstractRequest(Builder<?> builder) {
    assert (builder.path != null);
    assert (builder.host != null);
    assert (builder.port > 0);
    assert (builder.scheme != null);
    assert (builder.parameters != null);
    httpManager = builder.httpManager == null ? Api.DEFAULT_HTTP_MANAGER
        : builder.httpManager;
    if (builder.authorizationRequired && !httpManager.hasCredentials()) {
      throw new IllegalStateException("Authorization required.");
    }
    url = Url.newBuilder().setScheme(builder.scheme).setHost(builder.host)
        .setPort(builder.port).setPath(builder.path).addAllParameters(
            builder.parameters).build();
  }

  @Override
  public final String toString() {
     return UrlUtil.assemble(url);
  }

  public static abstract class Builder<BuilderType extends Builder<?>>
      implements Request.Builder {

    String path = null;
    HttpManager httpManager;
    boolean authorizationRequired = false;
    String host = Api.DEFAULT_HOST;
    int port = Api.DEFAULT_PORT;
    Url.Scheme scheme = Api.DEFAULT_SCHEME;
    List<Url.Parameter> parameters = new ArrayList<Url.Parameter>();

    @SuppressWarnings("unchecked")
    public BuilderType httpManager(HttpManager httpManager) {
      this.httpManager = httpManager;
      // Safe conversion because BuilderType extends Builder
      return (BuilderType) this;
    }

    @SuppressWarnings("unchecked")
    public BuilderType host(String host) {
      this.host = host;
      // Safe conversion because BuilderType extends Builder
      return (BuilderType) this;
    }

    @SuppressWarnings("unchecked")
    public BuilderType port(int port) {
      this.port = port;
      // Safe conversion because BuilderType extends Builder
      return (BuilderType) this;
    }

    @SuppressWarnings("unchecked")
    public BuilderType scheme(Url.Scheme scheme) {
      this.scheme = scheme;
      // Safe conversion because BuilderType extends Builder
      return (BuilderType) this;
    }

    @SuppressWarnings("unchecked")
    BuilderType parameter(String name, String value) {
      assert (name != null);
      assert (name.length() > 0);
      assert (value != null);
      Url.Parameter parameter = Url.Parameter.newBuilder().setName(name)
          .setValue(value).build();
      parameters.add(parameter);
      // Safe conversion because BuilderType extends Builder
      return (BuilderType) this;
    }
    
    @SuppressWarnings("unchecked")
    BuilderType path(String path) {
      this.path = path;
      return (BuilderType) this;
    }
    
    @SuppressWarnings("unchecked")
    BuilderType authorizationRequired(boolean authorizationRequired) {
      this.authorizationRequired = authorizationRequired;
      return (BuilderType) this;
    }
  }

  String getJson()  {
    return httpManager.get(url);
  }

  String postJson() {
    return httpManager.post(url);
  }
}
