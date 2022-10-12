package org.aibles.okr3.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

  public NotFoundException(String field, Object value, String type) {
    super();
    setCode("org.aibles.okr3.exception.NotFoundException");
    setStatus(HttpStatus.NOT_FOUND.value());
    addParams("field", field);
    addParams("value", value);
    addParams("type", type);
  }
}
