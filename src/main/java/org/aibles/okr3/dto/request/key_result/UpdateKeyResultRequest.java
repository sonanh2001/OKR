package org.aibles.okr3.dto.request.key_result;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aibles.okr3.entity.KeyResult;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateKeyResultRequest extends CreateKeyResultRequest {

  @NotNull(message = "an updated key result must have an id")
  private Long keyResultId;

  public KeyResult toKeyResult(KeyResult keyResult) {
    KeyResult keyResultUpdated = super.toKeyResult();
    keyResultUpdated.setKeyResultId(keyResult.getKeyResultId());
    return keyResultUpdated;
  }
}
