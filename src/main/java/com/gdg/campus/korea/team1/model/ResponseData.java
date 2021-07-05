package com.gdg.campus.korea.team1.model;

import lombok.Data;

@Data
public class ResponseData<T> {
  // HttpStatus
  private String status;
  // Http Default Message
  private String message;
  // Error Message to USER
  private String errorMessage;
  // Error Code
  private String errorCode;

  public ResponseData() {}

  public ResponseData(String status, String message, String errorCode, String errorMessage) {
    this.status = status;
    this.message = message;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
