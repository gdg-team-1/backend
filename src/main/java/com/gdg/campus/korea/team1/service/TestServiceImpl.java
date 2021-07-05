package com.gdg.campus.korea.team1.service;

import com.gdg.campus.korea.team1.mapper.TestMapper;
import com.gdg.campus.korea.team1.exception.TestException;
import com.gdg.campus.korea.team1.model.TestModel;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

  private final TestMapper testDao;

  @Autowired
  public TestServiceImpl(TestMapper testDao) {
    this.testDao = testDao;
  }

  public List<TestModel> findAll() {
    logger.info("find All");
    return testDao.findAll();
  }

  public TestModel findById(int id) {
    logger.info("find by id = {}", id);
    return testDao.findById(id);
  }

  public TestModel findByName(String name) {
    logger.info("find by name = {}", name);
    return testDao.findByName(name);
  }

  public void insert(TestModel testModel) {
    logger.info("insert = {}", testModel);
    testDao.insert(testModel);
  }

  public void update(TestModel newTestModel) {
    logger.info("update = {}", newTestModel);
    TestModel dbData = testDao.findById(newTestModel.getId());
    if (dbData == null) {
      throw new TestException("no db data");
    }
    dbData.setName(newTestModel.getName());
    dbData.setAge(newTestModel.getAge());
    dbData.setEmail(newTestModel.getEmail());
    testDao.update(newTestModel);
  }

  public void removeById(int id) {
    logger.info("delete by id = {}", id);
    testDao.delete(id);
  }
}
