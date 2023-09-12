package taskmanager.taskmanager.category;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Override
  Optional<Category> findById(@NonNull Long id);

  Optional<List<Category>> findByNameLikeIgnoreCase(String name);

}
