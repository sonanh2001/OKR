package org.aibles.okr3.exception;

import org.springframework.http.HttpStatus;

public class ExistedException extends BadRequestException {

  public ExistedException(String field, Object value, String type) {
    setCode("org.aibles.okr3.exception.ExistedException");
    setStatus(HttpStatus.BAD_REQUEST.value());
    addParams("field", field);
    addParams("value", value);
    addParams("type", type);
  }
}
