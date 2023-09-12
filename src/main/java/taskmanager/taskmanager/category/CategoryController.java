package taskmanager.taskmanager.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  public ResponseEntity<Category> createOne(@RequestBody CategoryCreateDto dto) {
    return ResponseEntity.ok().body(this.categoryService.createOne(dto));
  }

  @GetMapping
  public ResponseEntity<List<Category>> findAll() {
    return ResponseEntity.ok().body(this.categoryService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> findById(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(this.categoryService.findById(Long.parseLong(id)));
  }

  @GetMapping("/") // ?s=someName
  public ResponseEntity<List<Category>> findByName(@RequestParam String s) {
    return ResponseEntity.ok().body(this.categoryService.findByName(s));
  }
}
