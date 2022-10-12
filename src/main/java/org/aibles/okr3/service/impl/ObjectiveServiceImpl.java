package org.aibles.okr3.service.impl;

import static org.aibles.okr3.constants.CommonConstants.DUPLICATE_KEY_MESSAGE;
import static org.aibles.okr3.constants.CommonConstants.START_OF_PROGRESS;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.request.objective.CreateObjectiveRequest;
import org.aibles.okr3.dto.request.objective.UpdateObjectiveRequest;
import org.aibles.okr3.dto.response.key_result.KeyResultResponse;
import org.aibles.okr3.dto.response.objective.ObjectiveResponse;
import org.aibles.okr3.dto.response.objective.ObjectiveResponseDetail;
import org.aibles.okr3.entity.Objective;
import org.aibles.okr3.exception.BadRequestException;
import org.aibles.okr3.exception.ExistedException;
import org.aibles.okr3.exception.NotFoundException;
import org.aibles.okr3.repository.ObjectiveRepository;
import org.aibles.okr3.service.KeyResultService;
import org.aibles.okr3.service.ObjectiveService;
import org.aibles.okr3.service.UserService;
import org.aibles.okr3.utils.DateUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class ObjectiveServiceImpl implements ObjectiveService {

  private static final String OBJECTIVE_ID_FIELD = "objectiveId";

  private static final String OBJECTIVE_TITLE_FIELD = "title";

  private static final String OBJECTIVE_TYPE = "objective";

  private static final String USER_ID_FIELD = "userId";

  private static final String USER_TYPE = "user";

  private final ObjectiveRepository repository;

  private final KeyResultService keyResultService;

  private final UserService userService;

  /**
   * check date of objective invalid or not
   *
   * @param startDate - start date of objective
   * @param dueDate - due date of objective
   * @return true or false
   */
  private Boolean checkObjectiveDate(String startDate, String dueDate) {
    return DateUtil.convertStringToEpoch(dueDate) > DateUtil.convertStringToEpoch(startDate);
  }

  @Override
  @Transactional
  public ObjectiveResponse create(CreateObjectiveRequest request) {
    log.info("(create)title : {}", request.getTitle());
    if (repository.existsByTitle(request.getTitle())) {
      throw new ExistedException(OBJECTIVE_TITLE_FIELD, request.getTitle(), OBJECTIVE_TYPE);
    }
    if (!userService.existsById(request.getUserId())) {
      throw new NotFoundException(USER_ID_FIELD, request.getUserId(), USER_TYPE);
    }
    if (!checkObjectiveDate(request.getStartDate(), request.getDueDate())) {
      throw new BadRequestException();
    }
    try {
      return ObjectiveResponse.from(repository.save(request.toObjective()), START_OF_PROGRESS);
    } catch (DuplicateKeyException ex) {
      log.error("(create)exception : {}", ex.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }

  @Override
  @Transactional
  public void deleteById(long id) {
    log.info("(deleteById)id : {}", id);
    if (repository.existsById(id)) {
      keyResultService.deleteAllByObjectiveId(id);
      repository.deleteById(id);
    } else {
      throw new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Boolean existsById(long id) {
    log.info("(existsById)id : {}", id);
    return repository.existsById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ObjectiveResponse> findAllByUserId(long id) {
    log.info("(findAllByUserId)id : {}", id);
    if (userService.existsById(id)) {
      return repository.findAllByUserId(id).stream()
          .map(
              obj ->
                  ObjectiveResponse.from(
                      obj, keyResultService.getAvgProgressByObjectiveId(obj.getObjectiveId())))
          .collect(Collectors.toList());
    } else {
      throw new NotFoundException(USER_ID_FIELD, id, USER_TYPE);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ObjectiveResponseDetail getById(long id) {
    log.info("(getById)id : {}", id);
    Objective objective =
        repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE));
    List<KeyResultResponse> keyResultResponses = keyResultService.findAllByObjectiveId(id);
    return ObjectiveResponseDetail.from(
        objective, keyResultResponses, keyResultService.getAvgProgressByObjectiveId(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Long getDueDateById(long id) {
    log.info("(getDueDateById)id : {}", id);
    return repository
        .findDueDateById(id)
        .orElseThrow(() -> new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE));
  }

  @Override
  @Transactional(readOnly = true)
  public Long getStartDateById(long id) {
    log.info("(getStartDateById)id : {}", id);
    return repository
        .findStartDateById(id)
        .orElseThrow(() -> new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE));
  }

  @Override
  @Transactional
  public ObjectiveResponse update(UpdateObjectiveRequest request, long id) {
    log.info("(update)title : {}. id : {}", request.getTitle(), id);
    if (!userService.existsById(request.getUserId())) {
      throw new NotFoundException(USER_ID_FIELD, request.getUserId(), USER_TYPE);
    }
    if (!checkObjectiveDate(request.getStartDate(), request.getDueDate())) {
      throw new BadRequestException();
    }
    Objective objective =
        repository
            .findById(id)
            .map(request::toObjective)
            .orElseThrow(() -> new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE));
    try {
      return ObjectiveResponse.from(
          repository.save(objective), keyResultService.getAvgProgressByObjectiveId(id));
    } catch (DuplicateKeyException ex) {
      log.info("(update)exception : {}", ex.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }
}
