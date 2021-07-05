package com.gdg.campus.korea.team1.mapper;

import com.gdg.campus.korea.team1.model.TestModel;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface TestMapper {

  List<TestModel> findAll();

  TestModel findById(int id);

  TestModel findByName(String name);

  void update(TestModel testModel);

  void insert(TestModel testModel);

  void delete(int id);
}