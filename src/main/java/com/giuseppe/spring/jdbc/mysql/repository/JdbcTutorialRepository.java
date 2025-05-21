package com.giuseppe.spring.jdbc.mysql.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

@Repository
public class JdbcTutorialRepository implements TutorialRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int save(Tutorial tutorial) {
    return jdbcTemplate.update("INSERT INTO tutorials (title, description, published) VALUES(?,?,?)",
            tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
  }

  @Override
  public int update(Tutorial tutorial) {
    return jdbcTemplate.update("UPDATE tutorials SET title=?, description=?, published=? WHERE id=?",
            tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId());
  }

  @Override
  public Tutorial findById(Long id) {
    try {
      Tutorial tutorial = jdbcTemplate.queryForObject("SELECT * FROM tutorials WHERE id=?",
          BeanPropertyRowMapper.newInstance(Tutorial.class), id);

      return tutorial;
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update("DELETE FROM tutorials WHERE id=?", id);
  }

  @Override
  public List<Tutorial> findAll() {
    return jdbcTemplate.query("SELECT * from tutorials", BeanPropertyRowMapper.newInstance(Tutorial.class));
  }

  @Override
  public List<Tutorial> findByPublished(boolean published) {
    return jdbcTemplate.query("SELECT * from tutorials WHERE published=?",
        BeanPropertyRowMapper.newInstance(Tutorial.class), published);
  }

  @Override
  public List<Tutorial> findByTitleContaining(String title) {
//    String q = "SELECT * from tutorials WHERE title LIKE '%" + title + "%'";
//
//    return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
    // correzione della vulnerabilità SQL injection tramite query con parametri
    return jdbcTemplate.query("SELECT * from tutorials WHERE title LIKE ?",
            BeanPropertyRowMapper.newInstance(Tutorial.class), "%" + title + "%");
  }

  @Override
  public int deleteAll() {
    return jdbcTemplate.update("DELETE from tutorials");
  }

  // metodi con supporto per orderBy e limit
  @Override
  public List<Tutorial> findAll(String sort, String direction, int offset, int limit) {
    // validare i parametri per sicurezza
    sort = validateSortField(sort);
    direction = validateDirection(direction);

    String sql = "SELECT * FROM tutorials ORDER BY " + sort + " " + direction + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Tutorial.class), limit, offset);
  }

  @Override
  public List<Tutorial> findByPublished(boolean published, String sort, String direction, int offset, int limit) {
    // Validare i parametri per sicurezza
    sort = validateSortField(sort);
    direction = validateDirection(direction);

    String sql = "SELECT * FROM tutorials WHERE published=? ORDER BY " + sort + " " + direction + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Tutorial.class), published, limit, offset);
  }

  @Override
  public List<Tutorial> findByTitleContaining(String title, String sort, String direction, int offset, int limit) {
    // validare i parametri per sicurezza
    sort = validateSortField(sort);
    direction = validateDirection(direction);

    String sql = "SELECT * FROM tutorials WHERE title LIKE ? ORDER BY " + sort + " " + direction + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Tutorial.class), "%" + title + "%", limit, offset);
  }

  // metodi di utilità per la validazione
  private String validateSortField(String sort) {
    List<String> validFields = List.of("id", "title", "description", "published");
    return validFields.contains(sort.toLowerCase()) ? sort : "id";
  }

  private String validateDirection(String direction) {
    return "desc".equalsIgnoreCase(direction) ? "DESC" : "ASC";
  }
}
