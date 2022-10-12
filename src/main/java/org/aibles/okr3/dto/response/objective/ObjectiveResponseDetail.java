package org.aibles.okr3.dto.response.objective;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aibles.okr3.dto.response.key_result.KeyResultResponse;
import org.aibles.okr3.entity.Objective;
import org.aibles.okr3.utils.DateUtil;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectiveResponseDetail extends ObjectiveResponse {

  private List<KeyResultResponse> keyResults;

  public static ObjectiveResponseDetail from(
      Objective objective, List<KeyResultResponse> keyResults, Float progress) {
    ObjectiveResponseDetail responseDetail = new ObjectiveResponseDetail();
    responseDetail.setObjectiveId(objective.getObjectiveId());
    responseDetail.setTitle(objective.getTitle());
    responseDetail.setReason(objective.getReason());
    responseDetail.setMentor(objective.getMentor());
    responseDetail.setType(objective.getType());
    responseDetail.setDueDate(DateUtil.convertEpochToString(objective.getDueDate()));
    responseDetail.setStartDate(DateUtil.convertEpochToString(objective.getStartDate()));
    responseDetail.setProgress(progress);
    responseDetail.setKeyResults(keyResults);
    responseDetail.setUserId(objective.getUserId());
    return responseDetail;
  }
}
