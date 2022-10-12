package org.aibles.okr3.dto.response.objective;

import lombok.Data;
import org.aibles.okr3.entity.Objective;
import org.aibles.okr3.utils.DateUtil;

@Data
public class ObjectiveResponse {
  private long objectiveId;
  private String title;
  private String reason;
  private String mentor;
  private String type;
  private float progress;
  private String dueDate;
  private String startDate;
  private Long userId;

  public static ObjectiveResponse from(Objective objective, Float progress) {
    ObjectiveResponse response = new ObjectiveResponse();
    response.setObjectiveId(objective.getObjectiveId());
    response.setTitle(objective.getTitle());
    response.setReason(objective.getReason());
    response.setMentor(objective.getMentor());
    response.setType(objective.getType());
    response.setDueDate(DateUtil.convertEpochToString(objective.getDueDate()));
    response.setStartDate(DateUtil.convertEpochToString(objective.getStartDate()));
    response.setProgress(progress);
    response.setUserId(objective.getUserId());
    return response;
  }
}
