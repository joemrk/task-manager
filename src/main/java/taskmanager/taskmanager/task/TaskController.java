package taskmanager.taskmanager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskmanager.taskmanager.annotations.CurrentUser;
import taskmanager.taskmanager.user.User;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public ResponseEntity<List<Task>> findAll(@CurrentUser User currentUser) {
    return ResponseEntity.ok(this.taskService.findAll(currentUser));
  }

  // create task
  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody TaskCreateDto dto, @CurrentUser User currentUser) {
    return ResponseEntity.ok().body(this.taskService.createOne(dto, currentUser));
  }

  // get tasks - get by: tag, category, status

  // edit tasks - details, date, title, tags

  // delete task

  // done task (edit)

  //
}
