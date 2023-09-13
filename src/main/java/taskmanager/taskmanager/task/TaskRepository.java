package taskmanager.taskmanager.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  @Query("SELECT t FROM Task t LEFT JOIN FETCH t.tags WHERE t.id = :taskId")
  Task findByIdWithTags(@Param("taskId") Long taskId);

}
