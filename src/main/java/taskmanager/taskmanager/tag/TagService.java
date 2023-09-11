package taskmanager.taskmanager.tag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import jakarta.persistence.ParameterMode;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagService {
  private final JdbcTemplate jdbcTemplate;
  private final TagRepository tagRepository;
  private final EntityManager entityManager;

  @Autowired
  public TagService(JdbcTemplate jdbcTemplate, TagRepository tagRepository, EntityManager entityManager) {
    this.jdbcTemplate = jdbcTemplate;
    this.tagRepository = tagRepository;
    this.entityManager = entityManager;
  }

  public void createMany(List<String> tags) {
    Set<String> existTags = this.tagRepository.findMany(tags)
            .stream()
            .map(Tag::getName)
            .collect(Collectors.toSet());

    if(existTags.size() == tags.size()) {
      return;
    }

    List<String> newTagsList = tags.stream()
            .filter(t -> !existTags.contains(t))
            .toList();


    if(!newTagsList.isEmpty()) {
      List<Tag> newTags = newTagsList
              .stream()
              .map(t -> Tag.builder().name(t).build())
              .toList();

      this.tagRepository.saveAll(newTags);
    }
  }

  public void createManyWithIgnore(List<String> tags) {
//    Pros:
//    It's a SQL-based approach and can be efficient if you're dealing with a large number of tags.
//    The database handles the duplicate tag checking, which can be fast and reliable.
//    Cons:
//    It may be less portable across different database systems, as the SQL syntax for handling duplicates may vary.
//    You might need to be cautious about SQL injection if you're directly using user input in your SQL queries.
    String sql = "INSERT INTO tags (name) VALUES (?) ON CONFLICT (name) DO NOTHING";

    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement preparedStatement, int i)
              throws SQLException {
        preparedStatement.setString(1, tags.get(i));
      }

      @Override
      public int getBatchSize() {
        return tags.size();
      }
    });
  }

  public void createManyWithProcedure(List<String> tags) {
//    Pros:
//    Stored procedures can provide encapsulation and security by allowing controlled access to the database.
//    You have more control over the logic, which can be beneficial for complex scenarios.
//    Cons:
//    It adds an additional layer of complexity to your application.
//    Maintenance and debugging of stored procedures can be more challenging.
    String tagsString = String.join(",", tags);
    // procedure file = /resources/sql/

    try {
      StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("CreateManyWithProcedure");
      storedProcedure.registerStoredProcedureParameter("tags", String.class, ParameterMode.IN);
      storedProcedure.setParameter("tags", tagsString);
      storedProcedure.execute();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public List<Tag> findTags(String name) {
    return this.tagRepository.findByNameLikeIgnoreCase("%" + name + "%");
  }
}
