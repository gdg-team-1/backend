package com.gdg.campus.korea.team1.controller;

import com.gdg.campus.korea.team1.exception.TestException;
import com.gdg.campus.korea.team1.model.ResponseData;
import com.gdg.campus.korea.team1.model.TestModel;
import com.gdg.campus.korea.team1.service.TestServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

@RestController
@Api(value = "/")
public class HelloworldController {

  private final TestServiceImpl testService;

  @Autowired
  public HelloworldController(TestServiceImpl testService) {
    this.testService = testService;
  }

  @GetMapping("/hello")
  public ResponseEntity<TestModel> hello() {
    TestModel result = new TestModel("hello", 28, "test@gmail.com");
    result.setId(1);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/api/v1/user")
  @ApiOperation(value = "유저 전체 목록", notes = "성공시 유저 전체 정보를 가져옵니다.")
  public ResponseEntity<List<TestModel>> getTestList() {

    return new ResponseEntity<>(testService.findAll(), HttpStatus.OK);
  }

  @PostMapping("/api/v1/user")
  @ApiOperation(value = "유저 생성", notes = "성공시 등록한 정보의 유저가 생성됩니다.")
  public ResponseEntity<?> newTestModel(@RequestBody TestModel testModel) {
    testService.insert(testModel);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/v1/user/{id}")
  @ApiOperation(value = "유저 상세 정보", notes = "성공시 유저 정보를 가져옵니다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "id", value = "User ID", dataType = "int", required = true),
  })
  public ResponseEntity<TestModel> one(@PathVariable int id) {
    return new ResponseEntity<>(testService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/api/v1/user/search")
  @ApiOperation(value = "이름으로 유저 상세 정보", notes = "성공시 유저 정보를 가져옵니다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "name", value = "User name", dataType = "String", required = true, paramType = "query"),
  })
  public ResponseEntity<TestModel> getByName(@RequestParam String name) {
    return new ResponseEntity<>(testService.findByName(name), HttpStatus.OK);
  }

  @PutMapping("/api/v1/user/{id}")
  @ApiOperation(value = "유저 정보 수정", notes = "성공시 유저 정보를 수정합니다.")
  public ResponseEntity<?> replaceTestUser(@RequestBody TestModel testModel, @PathVariable int id) {
    testModel.setId(id);
    testService.update(testModel);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/api/v1/user/{id}")
  @ApiOperation(value = "유저 삭제", notes = "성공시 유저 정보를 삭제합니다.")
  public ResponseEntity<?> removeTestUser(@PathVariable int id) {
    testService.removeById(id);
    return ResponseEntity.ok().build();
  }

}
