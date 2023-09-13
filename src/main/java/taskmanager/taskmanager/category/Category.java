package taskmanager.taskmanager.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import taskmanager.taskmanager.task.Task;
import java.util.List;

@Data
@Entity
@Table(
  name = "categories",
  indexes = {@Index(columnList = "name")}
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true )
  private String name;

}
