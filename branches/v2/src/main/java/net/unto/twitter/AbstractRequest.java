package net.unto.twitter;

import net.unto.twitter.UtilProtos.Url;

import java.util.ArrayList;
import java.util.List;

class AbstractRequest {

  HttpManager httpManager = null;

  List<Url.Parameter> parameters = new ArrayList<Url.Parameter>();

  void AddParameter(String name, String value) {
    assert(name != null);
    assert(name.length() > 0);
    assert(value != null);
    parameters.add(
        Url.Parameter.newBuilder()
            .setName(name)
            .setValue(value)
            .build());
  }
}
