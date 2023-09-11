package taskmanager.taskmanager.tag;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/tags")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping
  public ResponseEntity<Number> createMany(@RequestBody List<String> createTagDto) {
    this.tagService.createMany(createTagDto);
    return ResponseEntity.ok().body(1);
  }

  @GetMapping("/{name}")
  public ResponseEntity<List<Tag>> findTags(@PathVariable String name) {
    List<Tag> tags = tagService.findTags(name);
    return ResponseEntity.ok().body(tags);
  }
}
