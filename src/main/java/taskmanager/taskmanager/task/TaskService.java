package taskmanager.taskmanager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskmanager.taskmanager.category.Category;
import taskmanager.taskmanager.exception.errors.BadRequestException;
import taskmanager.taskmanager.tag.Tag;
import taskmanager.taskmanager.tag.TagService;
import taskmanager.taskmanager.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final TagService tagService;

  @Autowired
  public TaskService(TaskRepository taskRepository, TagService tagService) {
    this.taskRepository = taskRepository;
    this.tagService = tagService;
  }


  public Task createOne(TaskCreateDto dto, User user){
    Set<Tag> tags = new HashSet<>(tagService.createMany(dto.getTags()));
    System.out.println("tags :" + tags);
    try {
      Task task = Task.builder()
              .description(dto.getDescription())
              .category(Category.builder().id(dto.getCategoryId()).build())
              .user(User.builder().id(user.getId()).build())
              .status(dto.getStatus())
              .tags(tags)
              .build();

      System.out.println(task);
      return this.taskRepository.save(task);
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage());
    }
  }

  public List<Task> findAll(User user) {
    List<Task> tasks = this.taskRepository.findByUserId(user.getId());
//    tasks.forEach(t -> {
//      Set<Tag> tag = t.getTags();
//      System.out.println(tag + ":" + tag.size());
//    });
//    tasks.forEach(t -> t.getTags().size());

    return tasks;
  }
}
