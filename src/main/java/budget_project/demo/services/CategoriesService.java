package budget_project.demo.services;

import budget_project.demo.dtos.CategoryRequestDTO;
import budget_project.demo.dtos.CategoryResponseDTO;
import budget_project.demo.miscellaneous.SimpleMessage;

import java.util.List;
import java.util.UUID;

public interface CategoriesService {

    CategoryResponseDTO createCategory(UUID userId, CategoryRequestDTO categoryRequest);
    List<CategoryResponseDTO> getUserCategories(UUID userId);
    SimpleMessage deleteCategory(UUID categoryId, UUID userId);
    CategoryResponseDTO updateCategory(UUID userId, UUID categoryId, CategoryRequestDTO requestUpdate);

}
