package com.gdg.campus.korea.team1.controller;

import com.gdg.campus.korea.team1.model.User;
import com.gdg.campus.korea.team1.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(value = "/api/v1/user")
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserServiceImpl userService;

  @Autowired
  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @GetMapping("/test")
  public ResponseEntity<User> test() {
    User result = new User();
    result.setId("trpark");
    result.setNickname("TR-Park");
    result.setProfileUrl("https://storage.googleapis.com/gdg-team1-alibaba-bucket/image/cat1.jpg");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("")
  @ApiOperation(value = "유저 전체 목록", notes = "성공시 유저 전체 정보를 가져옵니다.")
  public ResponseEntity<List<User>> getUserList() {

    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  @PostMapping("")
  @ApiOperation(value = "유저 생성", notes = "성공시 유저가 생성됩니다.")
  public ResponseEntity<String> newUser(
      @ApiParam(name = "user", value = "등록할 유저", required = true)
      @RequestBody User user) {
    userService.insert(user);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/detail/{id}")
  @ApiOperation(value = "유저 상세 정보", notes = "성공시 유저 정보를 가져옵니다.")
  @ApiImplicitParam(name = "id", value = "User ID", dataType = "String", required = true)
  public ResponseEntity<User> one(@PathVariable String id) {
    return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/search")
  @ApiOperation(value = "파라미터로 유저 목록 호출", notes = "성공시 유저 정보를 가져옵니다.")

  public ResponseEntity<User> getByParam(
      @ApiParam(name = "nickname", value = "nickname")
      @RequestParam(required = false) String nickname) {
    return new ResponseEntity<>(userService.findByNickname(nickname), HttpStatus.OK);
  }

  @PutMapping("/detail/{id}")
  @ApiOperation(value = "유저 정보 수정", notes = "성공시 유저 정보를 수정합니다.")
  public ResponseEntity<String> replaceTestUser(@RequestBody User user, @PathVariable String id) {
    user.setId(id);
    userService.update(user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/detail/{id}")
  @ApiOperation(value = "유저 삭제", notes = "성공시 유저 정보를 삭제합니다.")
  public ResponseEntity<String> remove(@PathVariable String id) {
    userService.remove(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/imageFileUpLoad")
  @ResponseBody
  public ResponseEntity<String>  handleFileUpload(@RequestPart MultipartFile file) {
    try {
      return new ResponseEntity<>(userService.store(file), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

  }

}
