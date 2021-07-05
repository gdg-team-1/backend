package com.gdg.campus.korea.team1.mapper;

import com.gdg.campus.korea.team1.model.Alibi;
import com.gdg.campus.korea.team1.model.Need;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface AlibiMapper {

  Alibi findById(int id);

  List<Alibi> findAll();

  Alibi findByRequestUser(String name);

  void update(Alibi alibi);

  void insertNeed(Need need);

  void insertAlibi(Alibi alibi);

  void deleteNeed(int alibiId);

  void deleteAlibi(int id);
}