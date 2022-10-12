package org.aibles.okr3.service;

import java.util.List;
import org.aibles.okr3.dto.request.user.LoginRequest;
import org.aibles.okr3.dto.request.user.ProfileUserRequest;
import org.aibles.okr3.dto.request.user.RegisterRequest;
import org.aibles.okr3.dto.response.objective.ObjectiveResponse;
import org.aibles.okr3.dto.response.user.LoginResponse;
import org.aibles.okr3.dto.response.user.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  /**
   * check user exist or not by user id
   *
   * @param id - id of a user
   * @return true or false
   */
  Boolean existsById(long id);
  /**
   * find a user by email
   *
   * @param email - an email of a user
   * @return a response for user after login
   */
  LoginResponse getByEmail(String email);

  /**
   * get all objective of a user
   *
   * @param id - id of a user
   * @return - a list response objectives of a user
   */
  List<ObjectiveResponse> getAllObjectiveById(long id);
  /**
   * get a user by an id
   *
   * @param id - id sent from client
   * @return a response of user to client
   */
  UserResponse getById(long id);

  /**
   * authenticate user
   *
   * @param request - login request from user
   * @return - a login response include jwt
   */
  LoginResponse login(LoginRequest request);
  /**
   * register an account
   *
   * @param request - a request from client
   */
  void register(RegisterRequest request);

  /**
   * update a user by id
   *
   * @param id - id sent from client
   * @param request - a request from client
   * @return a response of an updated user
   */
  UserResponse update(long id, ProfileUserRequest request);
}
