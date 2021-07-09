package com.gdg.campus.korea.team1.service;

import com.gdg.campus.korea.team1.consts.Consts;
import com.gdg.campus.korea.team1.consts.Consts.Google;
import com.gdg.campus.korea.team1.exception.TestException;
import com.gdg.campus.korea.team1.mapper.UserMapper;
import com.gdg.campus.korea.team1.model.User;
import com.gdg.campus.korea.team1.util.google.GcpStorageUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  // 2021-03-24T16:44:39.083+08:00
  private static final SimpleDateFormat PREFIX_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss.SSSXXX");

  private final UserMapper userMapper;

  private final ResourceLoader resourceLoader;

  // application.properties 파일 spring.cloud.gcp.project-id 확인
  @Value("${spring.cloud.gcp.project-id}")
  private String projectId;

  @Value("${spring.cloud.gcp.credentials.location}")
  private String credentialsPath;

  @Value("${gcp.storage.bucket}")
  private String bucketName;

  @Autowired
  public UserServiceImpl(UserMapper userMapper, ResourceLoader resourceLoader) {
    this.userMapper = userMapper;
    this.resourceLoader = resourceLoader;
  }

  public List<User> findAll() {
    logger.info("find All");
    return userMapper.findAll();
  }

  public User findById(String id) {
    logger.info("find by id = {}", id);
    return userMapper.findById(id);
  }

  public User findByNickname(String nickname) {
    logger.info("find by nickname = {}", nickname);
    return userMapper.findByNickname(nickname);
  }

  public void insert(User user) {
    if (userMapper.findById(user.getId()) != null) {
      throw new TestException("Error: Duplicated ID");
    }
    logger.info("insert = {}", user);
    userMapper.insert(user);
  }

  public void update(User newUser) {
    logger.info("update = {}", newUser);
    User dbData = userMapper.findById(newUser.getId());
    if (dbData == null) {
      throw new TestException("Error: No data in DB");
    }
    dbData.setNickname(newUser.getNickname());
    dbData.setProfileUrl(newUser.getProfileUrl());
    userMapper.update(newUser);
  }

  public void remove(String id) {
    logger.info("delete by id = {}", id);
    userMapper.delete(id);
  }

  public String store(MultipartFile file) throws Exception {
    logger.info("upload Google Storage = {}", file);
    GcpStorageUtil storageUtil = new GcpStorageUtil(resourceLoader, projectId, credentialsPath);

    String fileName = file.getOriginalFilename();

    if(!isImageFile(fileName)) {
      throw new TestException("Error: No input file");
    }
    UriComponentsBuilder resultUrl = UriComponentsBuilder.newInstance().scheme("https")
        .host(Google.STORAGE_ACCESS_URL).pathSegment(bucketName, "image");
    fileName = fileName.substring(fileName.lastIndexOf("/")+1);
    String prefixFileName = PREFIX_DATE.format(new Date(System.currentTimeMillis()));
    fileName = prefixFileName + "-" + fileName;
    storageUtil.uploadFile(file.getInputStream(), bucketName, "image/" + fileName);

    String result = resultUrl.path(fileName).build().toUriString();
    logger.info("link = {}", result);
    return result;
  }

  public static boolean isImageFile(String str) {
    if (!StringUtils.hasText(str)) {
      return false;
    }
    String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp|webp|jfif))$)";

    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(str);

    return m.matches();

  }
}
