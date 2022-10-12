package org.aibles.okr3.service.impl;

import static org.aibles.okr3.constants.CommonConstants.BEARER_TOKEN_INDEX;
import static org.aibles.okr3.constants.CommonConstants.START_OF_BEARER_TOKEN;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.response.user.JwtResponse;
import org.aibles.okr3.entity.RefreshToken;
import org.aibles.okr3.exception.UnauthorizedException;
import org.aibles.okr3.repository.RefreshTokenRepository;
import org.aibles.okr3.service.RefreshTokenService;
import org.aibles.okr3.service.UserService;
import org.aibles.okr3.utils.JWTUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository repository;

  private final UserService userService;

  @Override
  @Transactional
  public void create(String email, String refreshToken) {
    log.info("(create)email : {}, refreshToken : {}", email, refreshToken);
    if (repository.existsById(email)) {
      repository.deleteById(email);
    }
    repository.save(new RefreshToken(email, refreshToken));
  }

  @Override
  @Transactional
  public JwtResponse generateAccessToken(String authorizationHeader) {
    log.info("(generateAccessToken)header : {}", authorizationHeader);
    try {
      if (authorizationHeader != null && authorizationHeader.startsWith(START_OF_BEARER_TOKEN)) {
        String refreshToken = authorizationHeader.substring(BEARER_TOKEN_INDEX);

        String email = JWTUtil.getInstance().getEmailFromToken(refreshToken);
        UserDetails userDetails = userService.loadUserByUsername(email);
        if (JWTUtil.getInstance().validateToken(refreshToken, userDetails)) {
          return new JwtResponse(
              refreshToken, JWTUtil.getInstance().generateAccessToken(userDetails));
        }
      }
    } catch (Exception e) {
      throw new UnauthorizedException();
    }
    return null;
  }
}
