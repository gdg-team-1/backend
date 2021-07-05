package com.gdg.campus.korea.team1.controller;

import com.gdg.campus.korea.team1.model.Alibi;
import com.gdg.campus.korea.team1.model.Need;
import com.gdg.campus.korea.team1.service.AlibiServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/")
public class HelloworldController {

  private final AlibiServiceImpl alibiService;

  @Autowired
  public HelloworldController(AlibiServiceImpl alibiService) {
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
    need1.setAlibiId(1);
    need1.setTag("친구 사진만 필요");
    testNeeds.add(need1);
    Need need2 = new Need();
    need2.setAlibiId(1);
    need2.setTag("나와 같이 찍기");
    testNeeds.add(need2);
    result.setNeed(testNeeds);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/api/v1/alibi")
  @ApiOperation(value = "알리바이 전체 목록", notes = "성공시 알리바이 전체 정보를 가져옵니다.")
  public ResponseEntity<List<Alibi>> getAlibiList() {

    return new ResponseEntity<>(alibiService.findAll(), HttpStatus.OK);
  }

  @PostMapping("/api/v1/alibi")
  @ApiOperation(value = "알리바이 생성", notes = "성공시 알리바이가 생성됩니다.")
  public ResponseEntity<?> newAlibi(@RequestBody Alibi alibi) {
    alibiService.insert(alibi);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/v1/alibi/{id}")
  @ApiOperation(value = "알리바이 상세 정보", notes = "성공시 알리바이 정보를 가져옵니다.")
  @ApiImplicitParam(name = "id", value = "User ID", dataType = "int", required = true)
  public ResponseEntity<Alibi> one(@PathVariable int id) {
    return new ResponseEntity<>(alibiService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/api/v1/alibi/search")
  @ApiOperation(value = "작성자로 알리바이 상세 정보", notes = "성공시 알리바이 정보를 가져옵니다.")
  @ApiImplicitParam(name = "request user", value = "Request User", dataType = "String", required = true, paramType = "query")
  public ResponseEntity<Alibi> getByName(@RequestParam String requestUser) {
    return new ResponseEntity<>(alibiService.findByRequestUser(requestUser), HttpStatus.OK);
  }

  @PutMapping("/api/v1/alibi/{id}")
  @ApiOperation(value = "알리바이 정보 수정", notes = "성공시 알리바이 정보를 수정합니다.")
  public ResponseEntity<?> replaceTestUser(@RequestBody Alibi alibi, @PathVariable int id) {
    alibi.setId(id);
    alibiService.update(alibi);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/api/v1/user/{id}")
  @ApiOperation(value = "유저 삭제", notes = "성공시 유저 정보를 삭제합니다.")
  public ResponseEntity<?> removeTestUser(@PathVariable int id) {
    alibiService.removeById(id);
    return ResponseEntity.ok().build();
  }

}
