package org.aibles.okr3.dto.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @Email(message = "invalid email")
  private String email;

  @NotBlank(message = "password must not be blank")
  private String password;
}
