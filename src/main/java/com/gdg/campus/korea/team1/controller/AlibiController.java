package com.gdg.campus.korea.team1.controller;

import com.gdg.campus.korea.team1.model.Alibi;
import com.gdg.campus.korea.team1.model.Need;
import com.gdg.campus.korea.team1.service.AlibiServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/api/v1/alibi")
@RequestMapping("/api/v1/alibi")
public class AlibiController {

  private final AlibiServiceImpl alibiService;

  @Autowired
  public AlibiController(AlibiServiceImpl alibiService) {
    this.alibiService = alibiService;
  }

  @GetMapping("/test")
  public ResponseEntity<Alibi> test() {
    Alibi result = new Alibi();
    result.setId(1);
    result.setTitle("test title");
    result.setLocation("test location");
    result.setRequestUser("test request user");
    result.setDDay("2038-01-19 03:14:07");
    List<Need> testNeeds = new LinkedList<>();
    Need need1 = new Need();
    need1.setTag("친구 사진만 필요");
    testNeeds.add(need1);
    Need need2 = new Need();
    need2.setTag("나와 같이 찍기");
    testNeeds.add(need2);
    result.setNeed(testNeeds);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("")
  @ApiOperation(value = "알리바이 전체 목록", notes = "성공시 알리바이 전체 정보를 가져옵니다.")
  public ResponseEntity<List<Alibi>> getAlibiList() {

    return new ResponseEntity<>(alibiService.findAll(), HttpStatus.OK);
  }

  @PostMapping("")
  @ApiOperation(value = "알리바이 생성", notes = "성공시 알리바이가 생성됩니다.")
  public ResponseEntity<String> newAlibi(
      @ApiParam(name = "alibi", value = "등록할 알리바이", required = true)
      @RequestBody Alibi alibi) {
    alibiService.insert(alibi);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "알리바이 상세 정보", notes = "성공시 알리바이 정보를 가져옵니다.")
  @ApiImplicitParam(name = "id", value = "Alibi ID", dataType = "int", required = true)
  public ResponseEntity<Alibi> one(@PathVariable int id) {
    return new ResponseEntity<>(alibiService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/search")
  @ApiOperation(value = "작성자로 알리바이 상세 정보", notes = "성공시 알리바이 정보를 가져옵니다.")
  public ResponseEntity<Alibi> getByName(
      @ApiParam(name = "requestUser", value = "Request User", required = true)
      @RequestParam String requestUser) {
    return new ResponseEntity<>(alibiService.findByRequestUser(requestUser), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "알리바이 정보 수정", notes = "성공시 알리바이 정보를 수정합니다.")
  public ResponseEntity<String> replaceTestUser(@RequestBody Alibi alibi, @PathVariable int id) {
    alibi.setId(id);
    alibiService.update(alibi);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "알리바이 삭제", notes = "성공시 알리바이 정보를 삭제합니다.")
  public ResponseEntity<String> removeAlibi(@PathVariable int id) {
    alibiService.removeById(id);
    return ResponseEntity.ok().build();
  }

}
