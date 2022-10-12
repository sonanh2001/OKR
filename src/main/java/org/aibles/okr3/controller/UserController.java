package org.aibles.okr3.controller;

import static org.aibles.okr3.constants.ApiConstants.USERS_API_URI;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.request.user.LoginRequest;
import org.aibles.okr3.dto.request.user.ProfileUserRequest;
import org.aibles.okr3.dto.request.user.RegisterRequest;
import org.aibles.okr3.dto.response.objective.ObjectiveResponse;
import org.aibles.okr3.dto.response.user.LoginResponse;
import org.aibles.okr3.dto.response.user.UserResponse;
import org.aibles.okr3.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(USERS_API_URI)
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String register(@RequestBody @Valid RegisterRequest request) {
    log.info("(register)username: {}", request.getUsername());
    service.register(request);
    return "Register successfully!!!";
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse getById(@PathVariable long id) {
    log.info("(getById)id : {}", id);
    return service.getById(id);
  }

  @GetMapping("/{id}/objectives")
  @ResponseStatus(HttpStatus.OK)
  public List<ObjectiveResponse> getAllObjectiveById(@PathVariable("id") long id) {
    log.info("(getAllObjectiveById)id : {}", id);
    return service.getAllObjectiveById(id);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@RequestBody @Valid LoginRequest request) {
    log.info("(login)email : {}, password : {}", request.getEmail(), request.getPassword());
    return service.login(request);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse update(
      @PathVariable long id, @RequestBody @Valid ProfileUserRequest request) {
    log.info("(update)id : {}, username : {}", id, request.getUsername());
    return service.update(id, request);
  }
}
