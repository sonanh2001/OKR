package org.aibles.okr3.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

  public UnauthorizedException() {
    super();
    setCode("org.aibles.okr3.exception.UnauthorizedException");
    setStatus(HttpStatus.UNAUTHORIZED.value());
  }
}
