package com.gdg.campus.korea.team1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TestException extends RuntimeException {
  private static final long serialVersionUID = 2012168455513684070L;

  public TestException(String msg) {
    super(msg);
  }

  public TestException(int code) {
    super(String.valueOf(code));
  }

  public TestException(Exception ex) {
    super(ex);
  }
}