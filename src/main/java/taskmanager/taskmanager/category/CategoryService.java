package taskmanager.taskmanager.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskmanager.taskmanager.exception.errors.ConflictException;
import taskmanager.taskmanager.exception.errors.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

@Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Category findById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
  }

  public List<Category> findByName(String name) {
    return categoryRepository.findByNameLikeIgnoreCase("%" + name + "%")
            .orElse(List.of());
  }

  public Category createOne(CategoryCreateDto dto) {
    Optional<List<Category>> exist = this.categoryRepository.findByNameLikeIgnoreCase(dto.getName());
    if (exist.isEmpty()) {
      throw new ConflictException("Category already exists");
    }

    Category category = Category.builder()
            .name(dto.getName())
            .build();

    return this.categoryRepository.save(category);
  }
}
