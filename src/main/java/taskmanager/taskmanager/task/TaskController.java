package taskmanager.taskmanager.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

  @GetMapping
  public ResponseEntity<String> getTest() {
    return ResponseEntity.ok("test");
  }

  // create task

  // get tasks - get by: tag, category, status

  // edit tasks - details, date, title, tags

  // delete task

  // done task (edit)

  //
}
