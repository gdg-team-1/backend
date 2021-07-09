package com.gdg.campus.korea.team1.mapper;

import com.gdg.campus.korea.team1.model.Alibi;
import com.gdg.campus.korea.team1.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

  User findById(String id);

  List<User> findAll();

  User findByNickname(String nickname);

  void update(User user);

  void insert(User user);

  void delete(@Param(value = "id")String id);
}