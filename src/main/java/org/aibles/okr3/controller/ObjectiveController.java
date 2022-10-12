package org.aibles.okr3.controller;

import static org.aibles.okr3.constants.ApiConstants.OBJECTIVES_URI;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.request.objective.CreateObjectiveRequest;
import org.aibles.okr3.dto.request.objective.UpdateObjectiveRequest;
import org.aibles.okr3.dto.response.objective.ObjectiveResponse;
import org.aibles.okr3.dto.response.objective.ObjectiveResponseDetail;
import org.aibles.okr3.service.ObjectiveService;
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
@RequestMapping(OBJECTIVES_URI)
@RequiredArgsConstructor
@Slf4j
public class ObjectiveController {
  private final ObjectiveService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ObjectiveResponse create(@RequestBody @Valid CreateObjectiveRequest request) {
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
  public ObjectiveResponseDetail getById(@PathVariable long id) {
    log.info("(getById)id : {}", id);
    return service.getById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ObjectiveResponse update(
      @PathVariable long id, @RequestBody @Valid UpdateObjectiveRequest request) {
    log.info("(update)title : {}, id : {}", request.getTitle(), id);
    return service.update(request, id);
  }
}
