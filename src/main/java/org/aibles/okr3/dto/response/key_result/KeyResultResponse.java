package org.aibles.okr3.dto.response.key_result;

import lombok.Data;
import org.aibles.okr3.entity.KeyResult;
import org.aibles.okr3.utils.DateUtil;

@Data
public class KeyResultResponse {
  private long keyResultId;
  private float progress;
  private String title;
  private String step;
  private String mentor;
  private String startDate;
  private String dueDate;
  private long objectiveId;

  public static KeyResultResponse from(KeyResult keyResult) {
    KeyResultResponse response = new KeyResultResponse();
    response.setKeyResultId(keyResult.getKeyResultId());
    response.setProgress(keyResult.getProgress());
    response.setMentor(keyResult.getMentor());
    response.setStep(keyResult.getStep());
    response.setTitle(keyResult.getTitle());
    response.setObjectiveId(keyResult.getObjectiveId());
    response.setStartDate(DateUtil.convertEpochToString(keyResult.getStartDate()));
    response.setDueDate(DateUtil.convertEpochToString(keyResult.getDueDate()));
    return response;
  }
}
