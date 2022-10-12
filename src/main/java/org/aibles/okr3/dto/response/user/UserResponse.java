package org.aibles.okr3.dto.response.user;

import lombok.Data;
import org.aibles.okr3.entity.User;
import org.aibles.okr3.utils.DateUtil;

@Data
public class UserResponse {

  private long userId;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String dob;

  public UserResponse() {}

  public static UserResponse from(User user) {
    UserResponse response = new UserResponse();
    response.setUserId(user.getUserId());
    response.setUsername(user.getUsername());
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setEmail(user.getEmail());
    response.setPhone(user.getPhone());
    if (user.getDob() != null) {
      response.setDob(DateUtil.convertEpochToString(user.getDob()));
    }
    return response;
  }
}
