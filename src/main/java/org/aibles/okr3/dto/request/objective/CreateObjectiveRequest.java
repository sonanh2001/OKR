package org.aibles.okr3.dto.request.objective;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.aibles.okr3.entity.Objective;
import org.aibles.okr3.utils.DateUtil;

@Data
public class CreateObjectiveRequest {

  @NotBlank(message = "title must not blank")
  private String title;

  @NotBlank(message = "reason must not blank")
  private String reason;

  private String mentor;

  private String type;

  @NotBlank(message = "a objective must have a due date")
  @Pattern(
      regexp =
          "(?:(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2])|"
              + "(?:29|30)/(?:0[13-9]|1[0-2])|31/(?:0[13578]"
              + "|1[02]))/[1-9]\\d{3}|29/02(?:/[1-9]\\d(?:0[48]|[2468][048]|"
              + "[13579][26])|(?:[2468][048]|[13579][26])00)",
      message = "a date must have format dd/MM/yyyy")
  private String dueDate;

  @NotBlank(message = "a objective must have a due date")
  @Pattern(
      regexp =
          "(?:(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2])|"
              + "(?:29|30)/(?:0[13-9]|1[0-2])|31/(?:0[13578]"
              + "|1[02]))/[1-9]\\d{3}|29/02(?:/[1-9]\\d(?:0[48]|[2468][048]|"
              + "[13579][26])|(?:[2468][048]|[13579][26])00)",
      message = "a date must have format dd/MM/yyyy")
  private String startDate;

  @NotNull(message = "a objective must have user id")
  private Long userId;

  public Objective toObjective() {
    Objective objective = new Objective();
    objective.setTitle(this.getTitle());
    objective.setReason(this.getReason());
    objective.setMentor(this.getMentor());
    objective.setType(this.getType());
    objective.setDueDate(DateUtil.convertStringToEpoch(this.getDueDate()));
    objective.setStartDate(DateUtil.convertStringToEpoch(this.getStartDate()));
    objective.setUserId(this.getUserId());
    return objective;
  }
}
