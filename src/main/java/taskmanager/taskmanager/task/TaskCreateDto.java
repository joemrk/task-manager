package taskmanager.taskmanager.task;

import lombok.Data;
import java.util.List;

@Data
public class TaskCreateDto {
  private String description;
  private List<String> tags;
  private Long categoryId;
  private TaskStatus status = TaskStatus.TODO;
}
