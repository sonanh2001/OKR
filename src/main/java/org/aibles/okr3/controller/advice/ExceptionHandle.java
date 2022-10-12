package org.aibles.okr3.controller.advice;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.exception.BaseException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<Map<String, Object>> handle(BaseException ex) {
    log.info("(handle)exception : {}", ex.getCode());
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", ex.getStatus());
    errorMap.put("code", ex.getCode());
    if (ex.getParams() != null) {
      errorMap.putAll(ex.getParams());
    }
    return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(errorMap);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException ex) {
    log.info("(handle)exception: {}", ex.getClass().getName());
    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<Map<String, Object>> handle(DuplicateKeyException ex) {
    log.info("(handle)exception : {}", ex.getClass().getName());
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", HttpStatus.CONFLICT.value());
    errorMap.put("code", ex.getClass().getName());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
  }
}
