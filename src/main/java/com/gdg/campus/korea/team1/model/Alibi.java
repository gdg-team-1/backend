package com.gdg.campus.korea.team1.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class Alibi {

  @ApiModelProperty(example = "1")
  private int id;

  @ApiModelProperty(example = "trpark")
  private String requestUser;

  @ApiModelProperty(example = "ITZY 콘서트 대타가 필요해요")
  private String title;

  private List<String> category;

  @ApiModelProperty(example = "서울특별시 종로구 혜화동")
  private String location;

  @ApiModelProperty(example = "2038-01-19 03:14:07")
  private String dDay;

  public Alibi() {

  }
}
