package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;

public class UrlUtil {

  private UrlUtil() {}
  
  public static String assemble(Url url)  {
    String scheme = url.getScheme() == Url.Scheme.HTTP ? "http" : "https";
    try {
      URI uri = new URI(scheme, null, url.getHost(), url.getPort(), url.getPath());
      return uri.toString();
    } catch (URIException e) {
      throw new IllegalStateException(e);
    } 
  }
}
