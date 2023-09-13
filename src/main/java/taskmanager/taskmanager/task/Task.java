package taskmanager.taskmanager.task;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import taskmanager.taskmanager.category.Category;
import taskmanager.taskmanager.tag.Tag;
import taskmanager.taskmanager.user.User;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "description", length = 512)
  private String description;

  @ManyToOne
  @JoinColumn(name = "categoryId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Category category;

  @ManyToOne
  @JoinColumn(name = "userId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;


  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  public TaskStatus status;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
          name = "task_tags",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
  )
//  @JsonManagedReference
  private Set<Tag> tags  = new HashSet<>();;
}
