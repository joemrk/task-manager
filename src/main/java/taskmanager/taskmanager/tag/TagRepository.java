package taskmanager.taskmanager.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  @Procedure(name = "CreateManyWithProcedure")
  void createManyProcedure(@Param("tags") String tags);

  List<Tag> findByNameLikeIgnoreCase(String name);

  @Query(value = "select * from tags where name in (:tags)", nativeQuery = true)
  List<Tag> findMany(@Param("tags") List<String> tags);

  List<Tag> findTagsByTasksId(Long tutorialId);
}
