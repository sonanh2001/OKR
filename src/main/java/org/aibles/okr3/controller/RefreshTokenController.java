package org.aibles.okr3.controller;

import static org.aibles.okr3.constants.ApiConstants.REFRESH_TOKEN_URI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.response.user.JwtResponse;
import org.aibles.okr3.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(REFRESH_TOKEN_URI)
@RequiredArgsConstructor
@RestController
public class RefreshTokenController {

  private final RefreshTokenService service;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public JwtResponse refreshToken(
      @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
    log.info("(refreshToken)header : {}", authorizationHeader);
    return service.generateAccessToken(authorizationHeader);
  }
}
