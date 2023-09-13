package taskmanager.taskmanager.tag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import taskmanager.taskmanager.task.Task;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(
  name = "tags"
//  indexes = {@Index(columnList = "name")}
)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true )
  private String name;

  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
//  @JsonBackReference
  private Set<Task> tasks = new HashSet<>();

}
