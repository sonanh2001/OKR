package org.aibles.okr3.service.impl;

import static org.aibles.okr3.constants.CommonConstants.DUPLICATE_KEY_MESSAGE;
import static org.aibles.okr3.constants.CommonConstants.START_OF_PROGRESS;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.dto.request.key_result.CreateKeyResultRequest;
import org.aibles.okr3.dto.request.key_result.UpdateKeyResultRequest;
import org.aibles.okr3.dto.response.key_result.KeyResultResponse;
import org.aibles.okr3.entity.KeyResult;
import org.aibles.okr3.exception.BadRequestException;
import org.aibles.okr3.exception.ExistedException;
import org.aibles.okr3.exception.NotFoundException;
import org.aibles.okr3.repository.KeyResultRepository;
import org.aibles.okr3.service.KeyResultService;
import org.aibles.okr3.service.ObjectiveService;
import org.aibles.okr3.utils.DateUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class KeyResultServiceImpl implements KeyResultService {

  private static final String KEY_RESULT_ID_FIELD = "keyResultId";

  private static final String KEY_RESULT_TITLE_FIELD = "title";

  private static final String KEY_RESULT_TYPE = "keyResult";

  private static final String OBJECTIVE_ID_FIELD = "objectiveId";

  private static final String OBJECTIVE_TYPE = "objective";

  private final KeyResultRepository repository;

  private final @Lazy ObjectiveService objectiveService;

  /**
   * check date of key result valid or not
   *
   * @param startDate - start date of key result
   * @param dueDate - due date of key result
   * @param objectiveId - id of objective that own this key result
   * @return true or false
   */
  private Boolean checkKeyResultDate(String startDate, String dueDate, long objectiveId) {
    Long startDateKeyResult = DateUtil.convertStringToEpoch(startDate);
    Long dueDateKeyResult = DateUtil.convertStringToEpoch(dueDate);
    Long startDateObjective = objectiveService.getStartDateById(objectiveId);
    Long dueDateObjective = objectiveService.getDueDateById(objectiveId);
    if (dueDateKeyResult < startDateKeyResult) {
      return false;
    }

    if (startDateKeyResult < startDateObjective || startDateKeyResult > dueDateObjective) {
      return false;
    }

    return dueDateKeyResult > startDateObjective && dueDateKeyResult <= dueDateObjective;
  }

  @Override
  @Transactional
  public KeyResultResponse create(CreateKeyResultRequest request) {
    log.info("(create)title : {}", request.getTitle());
    if (repository.existsByTitle(request.getTitle())) {
      throw new ExistedException(KEY_RESULT_TITLE_FIELD, request.getTitle(), KEY_RESULT_TYPE);
    }
    if (!objectiveService.existsById(request.getObjectiveId())) {
      throw new NotFoundException(OBJECTIVE_ID_FIELD, request.getObjectiveId(), OBJECTIVE_TYPE);
    }
    if (!checkKeyResultDate(
        request.getStartDate(), request.getDueDate(), request.getObjectiveId())) {
      throw new BadRequestException();
    }
    try {
      return KeyResultResponse.from(repository.save(request.toKeyResult()));
    } catch (DuplicateKeyException ex) {
      log.error("(create)exception : {}", ex.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }

  @Override
  @Transactional
  public void deleteAllByObjectiveId(long id) {
    log.info("(deleteAllByObjectiveId)id : {}", id);
    if (objectiveService.existsById(id)) {
      repository.deleteAllByObjectiveId(id);
    } else {
      throw new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE);
    }
  }

  @Override
  @Transactional
  public void deleteById(long id) {
    log.info("(deleteById)id : {}", id);
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new NotFoundException(KEY_RESULT_ID_FIELD, id, KEY_RESULT_TYPE);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<KeyResultResponse> findAllByObjectiveId(long id) {
    log.info("(findAllByObjectiveId)id : {}", id);
    if (objectiveService.existsById(id)) {
      return repository.findAllByObjectiveId(id).stream()
          .map(KeyResultResponse::from)
          .collect(Collectors.toList());
    } else {
      throw new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public KeyResultResponse getById(long id) {
    log.info("(getById)id : {}", id);
    return repository
        .findById(id)
        .map(KeyResultResponse::from)
        .orElseThrow(() -> new NotFoundException(KEY_RESULT_ID_FIELD, id, KEY_RESULT_TYPE));
  }

  @Override
  @Transactional(readOnly = true)
  public Float getAvgProgressByObjectiveId(long id) {
    log.info("(getAvgProgressByObjectiveId)id : {}", id);
    if (objectiveService.existsById(id)) {
      return repository.getAvgProgressByObjectiveId(id).orElse(START_OF_PROGRESS);
    } else {
      throw new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE);
    }
  }

  @Override
  @Transactional
  public KeyResultResponse update(UpdateKeyResultRequest request, long id) {
    log.info("(update)title : {}, id : {}", request.getTitle(), id);
    if (!objectiveService.existsById(request.getObjectiveId())) {
      throw new NotFoundException(OBJECTIVE_ID_FIELD, id, OBJECTIVE_TYPE);
    }
    if (!checkKeyResultDate(
        request.getStartDate(), request.getDueDate(), request.getObjectiveId())) {
      throw new BadRequestException();
    }
    KeyResult keyResult =
        repository
            .findById(id)
            .map(request::toKeyResult)
            .orElseThrow(() -> new NotFoundException(KEY_RESULT_ID_FIELD, id, KEY_RESULT_TYPE));
    try {
      return KeyResultResponse.from(repository.save(keyResult));
    } catch (DuplicateKeyException ex) {
      log.error("(update)exception : {}", ex.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }
}
