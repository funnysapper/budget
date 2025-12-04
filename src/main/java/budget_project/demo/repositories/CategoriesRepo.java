package budget_project.demo.repositories;

import budget_project.demo.entities.Categories;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepo extends JpaRepository<Categories, UUID> {
    List<Categories> findByUserId(UUID userId);
    Optional<Categories> findByCategoryId(UUID categoryId);
    @Query(
            nativeQuery = true, value= "SELECT CASE WHEN COUNT(*)>0 THEN true ELSE false END"+
            "FROM categories WHERE categoryId =:categoryId AND userId = :userId"
    )
    boolean existsByCategoryIdAndUserId(@Param("categoryId") UUID categoryId,@Param("userId") UUID userId);
}
