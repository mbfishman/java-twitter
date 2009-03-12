package net.unto.twitter.methods;

import net.unto.twitter.JsonUtil;
import net.unto.twitter.TwitterProtos.User;
import net.unto.twitter.methods.FriendsTimelineRequest.Builder;


public final class UpdateProfileColorsRequest extends AbstractRequest {

  public static Builder builder() {
    return new Builder();
  }

  UpdateProfileColorsRequest(Builder builder) {
    super(builder);
  }

  public static final class Builder extends AbstractRequest.Builder<Builder> {

    Builder() {
      path("/account/update_profile_image.json");
      authorizationRequired(true);
    }

    public Builder profileBackgroundColor(String profileBackgroundColor) {
      return parameter("profile_background_color", profileBackgroundColor);
    }

    public Builder profileTextColor(String profileTextColor) {
      return parameter("profile_text_color", profileTextColor);
    }

    public Builder profileLinkColor(String profileLinkColor) {
      return parameter("profile_link_color", profileLinkColor);
    }

    public Builder profileSidebarFillColor(String profileSidebarFillColor) {
      return parameter("profile_sidebar_fill_color", profileSidebarFillColor);
    }

    public Builder profileSidebarBorderColor(String profileSidebarBorderColor) {
      return parameter("profile_sidebar_border_color",
          profileSidebarBorderColor);
    }

    public UpdateProfileColorsRequest build() {
      // Validate that: "Each parameter's value must be a valid hexidecimal 
      // value, and may be either three or six characters (ex: #fff or #ffffff)."
      return new UpdateProfileColorsRequest(this);
    }
  }

  public User get() {
    return JsonUtil.newUser(postJson());
  }
}
