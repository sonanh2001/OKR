package org.aibles.okr3.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

  public BadRequestException() {
    super();
    setCode("org.aibles.okr3.exception.BadRequestException");
    setStatus(HttpStatus.BAD_REQUEST.value());
  }
}
