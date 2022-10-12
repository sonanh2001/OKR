package org.aibles.okr3.service;

import org.aibles.okr3.dto.response.user.JwtResponse;

public interface RefreshTokenService {

  /**
   * create new refresh token
   *
   * @param email - email of user
   * @param refreshToken - refresh token from filter
   */
  void create(String email, String refreshToken);

  /**
   * generate new access token
   *
   * @param authorizationHeader - header request from client
   * @return a response of jwt
   */
  JwtResponse generateAccessToken(String authorizationHeader);
}
