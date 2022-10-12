package org.aibles.okr3.dto.request.objective;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aibles.okr3.entity.Objective;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateObjectiveRequest extends CreateObjectiveRequest {

  @NotNull(message = "an updated objective must have id")
  private Long objectiveId;

  public Objective toObjective(Objective objective) {
    Objective objectiveUpdated = super.toObjective();
    objectiveUpdated.setObjectiveId(objective.getObjectiveId());
    return objectiveUpdated;
  }
}
