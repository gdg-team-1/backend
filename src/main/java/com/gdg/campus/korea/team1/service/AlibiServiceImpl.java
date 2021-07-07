package com.gdg.campus.korea.team1.service;

import com.gdg.campus.korea.team1.exception.TestException;
import com.gdg.campus.korea.team1.mapper.AlibiMapper;
import com.gdg.campus.korea.team1.model.Alibi;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlibiServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(AlibiServiceImpl.class);

  private final AlibiMapper alibiMapper;

  @Autowired
  public AlibiServiceImpl(AlibiMapper alibiMapper) {
    this.alibiMapper = alibiMapper;
  }

  public List<Alibi> findAll() {
    logger.info("find All");
    return alibiMapper.findAll();
  }

  public Alibi findById(int id) {
    logger.info("find by id = {}", id);
    return alibiMapper.findById(id);
  }
  public List<Alibi> findByParam(Alibi param) {
    logger.info("find by param = {}", param);
    return alibiMapper.findByParam(param);
  }

  public void insert(Alibi alibi) {
    logger.info("insert = {}", alibi);
    alibiMapper.insertAlibi(alibi);
    logger.info("insert category");
    for (String cate : alibi.getCategory()) {
      alibiMapper.insertCategory(alibi.getId(), cate);
      logger.info("{}", cate);
    }
    logger.info("Done insert category");
  }

  public void update(Alibi newAlibi) {
    logger.info("update = {}", newAlibi);
    Alibi dbData = alibiMapper.findById(newAlibi.getId());
    if (dbData == null) {
      throw new TestException("no db data");
    }
    dbData.setRequestUser(newAlibi.getRequestUser());
    dbData.setTitle(newAlibi.getTitle());
    dbData.setLocation(newAlibi.getLocation());
    dbData.setDDay(newAlibi.getDDay());
    dbData.setCategory(newAlibi.getCategory());
    alibiMapper.deleteCategory(newAlibi.getId());
    for (String cate : newAlibi.getCategory()) {
      alibiMapper.insertCategory(dbData.getId(), cate);
    }
    alibiMapper.update(newAlibi);
  }

  public void removeById(int id) {
    logger.info("delete by id = {}", id);
    alibiMapper.deleteCategory(id);
    alibiMapper.deleteAlibi(id);
  }
}
