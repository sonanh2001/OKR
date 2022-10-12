package org.aibles.okr3.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.request.user.LoginRequest;
import org.aibles.okr3.dto.request.user.ProfileUserRequest;
import org.aibles.okr3.dto.request.user.RegisterRequest;
import org.aibles.okr3.dto.response.objective.ObjectiveResponse;
import org.aibles.okr3.dto.response.user.LoginResponse;
import org.aibles.okr3.dto.response.user.UserResponse;
import org.aibles.okr3.entity.User;
import org.aibles.okr3.exception.ExistedException;
import org.aibles.okr3.exception.NotFoundException;
import org.aibles.okr3.exception.UnauthorizedException;
import org.aibles.okr3.repository.UserRepository;
import org.aibles.okr3.service.ObjectiveService;
import org.aibles.okr3.service.RefreshTokenService;
import org.aibles.okr3.service.UserService;
import org.aibles.okr3.utils.JWTUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private static final String EMAIL_FIELD = "email";
  private static final String USERNAME_FIELD = "username";
  private static final String USER_ID_FIELD = "userId";
  private static final String USER_TYPE = "user";
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final ObjectiveService objectiveService;

  private final RefreshTokenService refreshTokenService;

  @Override
  @Transactional(readOnly = true)
  public Boolean existsById(long id) {
    log.info("(existsById)id : {}", id);
    return repository.existsById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public LoginResponse getByEmail(String email) {
    log.info("(getByEmail)email : {}", email);
    return LoginResponse.from(
        repository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(EMAIL_FIELD, email, USER_TYPE)));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ObjectiveResponse> getAllObjectiveById(long id) {
    log.info("(getAllObjectiveById)id : {}", id);
    if (!repository.existsById(id)) {
      throw new NotFoundException(USER_ID_FIELD, id, USER_TYPE);
    }
    return objectiveService.findAllByUserId(id);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getById(long id) {
    log.info("(getById)id : {}", id);
    return UserResponse.from(
        repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(USER_ID_FIELD, id, USER_TYPE)));
  }

  @Override
  @Transactional
  public LoginResponse login(LoginRequest request) {
    log.info("(login)email : {}, password : {}", request.getEmail(), request.getPassword());
    UserDetails userDetails = loadUserByUsername(request.getEmail());
    if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
      log.info("password : {}", userDetails.getPassword());
      throw new UnauthorizedException();
    }
    String accessToken = JWTUtil.getInstance().generateAccessToken(userDetails);
    String refreshToken = JWTUtil.getInstance().generateRefreshToken(userDetails);
    refreshTokenService.create(request.getEmail(), refreshToken);
    LoginResponse response = getByEmail(request.getEmail());
    response.setAccessToken(accessToken);
    response.setRefreshToken(refreshToken);
    return response;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("(loadUserByUsername)email : {}", username);
    User user = repository.findByEmail(username).orElseThrow(UnauthorizedException::new);
    return new UserDetailsImpl(user);
  }

  @Override
  @Transactional
  public void register(RegisterRequest request) {
    log.info("(register)username: {}", request.getUsername());
    if (repository.existsByEmail(request.getEmail())) {
      throw new ExistedException(EMAIL_FIELD, request.getEmail(), USER_TYPE);
    }
    if (repository.existsByUsername(request.getUsername())) {
      throw new ExistedException(USERNAME_FIELD, request.getUsername(), USER_TYPE);
    }
    User user = request.toUser();
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    repository.save(user);
  }

  @Override
  @Transactional
  public UserResponse update(long id, ProfileUserRequest request) {
    log.info("(update)id : {}, username: {}", id, request.getUsername());
    User user =
        repository
            .findById(id)
            .map(request::toUser)
            .orElseThrow(() -> new NotFoundException(USER_ID_FIELD, id, USER_TYPE));
    return UserResponse.from(repository.save(user));
  }
}
