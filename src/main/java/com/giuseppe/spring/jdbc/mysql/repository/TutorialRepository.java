package com.giuseppe.spring.jdbc.mysql.repository;

import java.util.List;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

public interface TutorialRepository {
  int save(Tutorial book);

  int update(Tutorial book);

  Tutorial findById(Long id);

  int deleteById(Long id);

  List<Tutorial> findAll();

  List<Tutorial> findByPublished(boolean published);

  List<Tutorial> findByTitleContaining(String title);

  int deleteAll();

  // metodi con supporto per orderBy e limit
  List<Tutorial> findAll(String sort, String direction, int offset, int limit);

  List<Tutorial> findByPublished(boolean published, String sort, String direction, int offset, int limit);

  List<Tutorial> findByTitleContaining(String title, String sort, String direction, int offset, int limit);
}
