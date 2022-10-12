package org.aibles.okr3.service;

import java.util.List;
import org.aibles.okr3.dto.request.key_result.CreateKeyResultRequest;
import org.aibles.okr3.dto.request.key_result.UpdateKeyResultRequest;
import org.aibles.okr3.dto.response.key_result.KeyResultResponse;

public interface KeyResultService {

  /**
   * create a key result
   *
   * @param request - a request from client
   * @return a response of a created key result
   */
  KeyResultResponse create(CreateKeyResultRequest request);

  /**
   * delete all key results of an objective
   *
   * @param id - id of an objective
   */
  void deleteAllByObjectiveId(long id);

  /**
   * delete a key result by id
   *
   * @param id - id of a key result
   */
  void deleteById(long id);

  /**
   * find all key results of an objective
   *
   * @param id - id of an objective
   * @return a list of key results of an objective
   */
  List<KeyResultResponse> findAllByObjectiveId(long id);

  /**
   * get a key result by id
   *
   * @param id - an id of a key result
   * @return a response of a key result
   */
  KeyResultResponse getById(long id);

  /**
   * get average of progress of key results of an objective
   *
   * @param id - id of an objective
   * @return average of progress by key results of an objective
   */
  Float getAvgProgressByObjectiveId(long id);

  /**
   * update a key result by id
   *
   * @param request - a request from client
   * @param id - an id of a key result
   * @return a response of an updated key result
   */
  KeyResultResponse update(UpdateKeyResultRequest request, long id);
}
