package org.aibles.okr3.dto.response.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aibles.okr3.entity.User;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends JwtResponse {
  private long userId;

  private String username;

  private String email;

  public static LoginResponse from(User user) {
    LoginResponse response = new LoginResponse();
    response.setUserId(user.getUserId());
    response.setEmail(user.getEmail());
    response.setUsername(user.getUsername());
    return response;
  }
}
