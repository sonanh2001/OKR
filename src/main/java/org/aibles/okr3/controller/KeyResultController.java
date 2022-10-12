package org.aibles.okr3.controller;

import static org.aibles.okr3.constants.ApiConstants.KEY_RESULTS_URI;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.request.key_result.CreateKeyResultRequest;
import org.aibles.okr3.dto.request.key_result.UpdateKeyResultRequest;
import org.aibles.okr3.dto.response.key_result.KeyResultResponse;
import org.aibles.okr3.service.KeyResultService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(KEY_RESULTS_URI)
@RequiredArgsConstructor
@Slf4j
public class KeyResultController {
  private final KeyResultService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public KeyResultResponse create(@RequestBody @Valid CreateKeyResultRequest request) {
    log.info("(create)title : {}", request.getTitle());
    return service.create(request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteById(@PathVariable long id) {
    log.info("(deleteById)id : {}", id);
    service.deleteById(id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public KeyResultResponse getById(@PathVariable long id) {
    log.info("(getById)id : {}", id);
    return service.getById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public KeyResultResponse update(
      @PathVariable long id, @RequestBody @Valid UpdateKeyResultRequest request) {
    log.info("(update)title : {}, id : {}", request.getTitle(), id);
    return service.update(request, id);
  }
}
