package org.aibles.okr3.service;

import java.util.List;
import org.aibles.okr3.dto.request.objective.CreateObjectiveRequest;
import org.aibles.okr3.dto.request.objective.UpdateObjectiveRequest;
import org.aibles.okr3.dto.response.objective.ObjectiveResponse;
import org.aibles.okr3.dto.response.objective.ObjectiveResponseDetail;

public interface ObjectiveService {

  /**
   * create an objective
   *
   * @param request - a request from client
   * @return a response of created objective
   */
  ObjectiveResponse create(CreateObjectiveRequest request);

  /**
   * delete an objective by id
   *
   * @param id - id of an objective
   */
  void deleteById(long id);

  /**
   * check an objective exist or not
   *
   * @param id - id of an objective
   * @return true or false
   */
  Boolean existsById(long id);

  /**
   * find all objective of a user by user id
   *
   * @param id - id of a user
   * @return list of objective response
   */
  List<ObjectiveResponse> findAllByUserId(long id);

  /**
   * @param id - id of an objective
   * @return response detail of an objective
   */
  ObjectiveResponseDetail getById(long id);

  /**
   * get due date of an objective
   *
   * @param id - id of an objective
   * @return due date in epoch format
   */
  Long getDueDateById(long id);

  /**
   * get start date of an objective
   *
   * @param id - id of an objective
   * @return start date in epoch format
   */
  Long getStartDateById(long id);

  /**
   * update an objective by id
   *
   * @param request - a request form client
   * @param id - id of an objective
   * @return a response of updated objective
   */
  ObjectiveResponse update(UpdateObjectiveRequest request, long id);
}
