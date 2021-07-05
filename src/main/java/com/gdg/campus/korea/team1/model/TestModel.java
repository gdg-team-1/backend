package com.gdg.campus.korea.team1.model;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TestModel {

  @ApiModelProperty(example = "1")
  private int id;

  @NotEmpty
  @ApiModelProperty(example = "홍길동")
  private String name;

  @ApiModelProperty(example = "32")
  private int age;

  @ApiModelProperty(example = "example@gmail.com")
  private String email;

  public TestModel() {

  }

  public TestModel(String name, int age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
  }
}
