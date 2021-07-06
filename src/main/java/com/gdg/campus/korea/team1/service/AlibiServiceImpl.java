package com.gdg.campus.korea.team1.service;

import com.gdg.campus.korea.team1.exception.TestException;
import com.gdg.campus.korea.team1.mapper.AlibiMapper;
import com.gdg.campus.korea.team1.model.Alibi;
import com.gdg.campus.korea.team1.model.Need;
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

  public Alibi findByRequestUser(String name) {
    logger.info("find by request user = {}", name);
    return alibiMapper.findByRequestUser(name);
  }

  public void insert(Alibi alibi) {
    logger.info("insert = {}", alibi);
    alibiMapper.insertAlibi(alibi);
    logger.info("insert need");
    for (Need tag : alibi.getNeed()) {
      tag.setAlibiId(alibi.getId());
      alibiMapper.insertNeed(tag);
      logger.info("insert tag = {}", tag);
    }
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
    dbData.setNeed(newAlibi.getNeed());
    alibiMapper.deleteNeed(newAlibi.getId());
    for (Need tag : newAlibi.getNeed()) {
      tag.setAlibiId(dbData.getId());
      alibiMapper.insertNeed(tag);
    }
    alibiMapper.update(newAlibi);
  }

  public void removeById(int id) {
    logger.info("delete by id = {}", id);
    alibiMapper.deleteNeed(id);
    alibiMapper.deleteAlibi(id);
  }
}
