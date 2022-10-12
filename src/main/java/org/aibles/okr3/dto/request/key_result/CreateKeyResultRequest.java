package org.aibles.okr3.dto.request.key_result;

import static org.aibles.okr3.constants.CommonConstants.START_OF_PROGRESS;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.aibles.okr3.entity.KeyResult;
import org.aibles.okr3.utils.DateUtil;

@Data
public class CreateKeyResultRequest {
  @NotBlank(message = "title must not blank")
  private String title;

  @NotBlank(message = "step must not blank")
  private String step;

  private String mentor;

  @NotBlank(message = "start date must not blank")
  @Pattern(
      regexp =
          "(?:(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2])|(?:29|30)"
              + "/(?:0[13-9]|1[0-2])|31/(?:0[13578]"
              + "|1[02]))/[1-9]\\d{3}|29/02(?:/[1-9]\\d(?:0[48]|[2468][048]|"
              + "[13579][26])|(?:[2468][048]|[13579][26])00)",
      message = "a date must have format dd/MM/yyyy")
  private String startDate;

  @NotBlank(message = "due date must not blank")
  @Pattern(
      regexp =
          "(?:(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2])|(?:29|30)/(?:0[13-9]|1[0-2])|31/(?:0[13578]"
              + "|1[02]))/[1-9]\\d{3}|29/02(?:/[1-9]\\d(?:0[48]|[2468][048]|"
              + "[13579][26])|(?:[2468][048]|[13579][26])00)",
      message = "a date must have format dd/MM/yyyy")
  private String dueDate;

  private Float progress;

  @NotNull(message = "an key result must belong to a objective")
  private Long objectiveId;

  public KeyResult toKeyResult() {
    KeyResult keyResult = new KeyResult();
    keyResult.setTitle(this.getTitle());
    keyResult.setStep(this.getStep());
    keyResult.setMentor(this.getMentor());
    keyResult.setDueDate(DateUtil.convertStringToEpoch(this.getDueDate()));
    keyResult.setStartDate(DateUtil.convertStringToEpoch(this.getStartDate()));
    keyResult.setObjectiveId(this.getObjectiveId());
    if (this.progress == null) {
      this.progress = START_OF_PROGRESS;
    }
    keyResult.setProgress(this.getProgress());
    return keyResult;
  }
}
