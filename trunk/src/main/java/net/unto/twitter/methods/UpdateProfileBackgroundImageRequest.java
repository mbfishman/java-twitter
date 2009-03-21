package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterProtos.User;

public final class UpdateProfileBackgroundImageRequest extends AbstractRequest {

  static final int MAX_IMAGE_SIZE = 700 * 1000; // 700KB
  
  public static Builder builder(byte[] imageData) {
    return new Builder(imageData);
  }
  
  UpdateProfileBackgroundImageRequest(Builder builder)  {
    super(builder);
  }
  
  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder(byte[] imageData) {
      assert(imageData != null);
      assert(imageData.length < MAX_IMAGE_SIZE);
      path("/account/update_profile_background_image.json");
      authorizationRequired(true);
      parameter("image", new String(imageData));
    }
    
    public UpdateProfileBackgroundImageRequest build() {
      return new UpdateProfileBackgroundImageRequest(this);
    }
  }

  public User post() {
    return JsonUtil.newUser(postJson());
  }
}
