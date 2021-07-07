package com.gdg.campus.korea.team1.mapper;

import com.gdg.campus.korea.team1.model.Alibi;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AlibiMapper {

  Alibi findById(int id);

  List<Alibi> findAll();

  List<Alibi> findByParam(Alibi alibi);

  void update(Alibi alibi);

  void insertCategory(@Param(value = "alibiId") int alibiId, @Param(value = "category") String category);

  void insertAlibi(Alibi alibi);

  void deleteCategory(@Param(value = "alibiId")int alibiId);

  void deleteAlibi(@Param(value = "id")int id);
}