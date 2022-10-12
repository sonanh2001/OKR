package org.aibles.okr3.dto.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.aibles.okr3.entity.User;

@Data
public class RegisterRequest {
  @NotBlank(message = "username must not be blank")
  private String username;

  @NotBlank(message = "password must not be blank")
  private String password;

  @Email(message = "invalid email")
  @NotBlank(message = "email must not be blank")
  private String email;

  public User toUser() {
    User user = new User();
    user.setUsername(this.getUsername());
    user.setEmail(this.getEmail());
    return user;
  }
}
