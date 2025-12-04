package budget_project.demo.serviceImpls;

import budget_project.demo.dtos.CategoryRequestDTO;
import budget_project.demo.dtos.CategoryResponseDTO;
import budget_project.demo.entities.Categories;
import budget_project.demo.exceptions.CategoryNotFoundException;
import budget_project.demo.exceptions.ResourceNotFoundException;
import budget_project.demo.exceptions.UnauthorizedException;
import budget_project.demo.miscellaneous.SimpleMessage;
import budget_project.demo.repositories.CategoriesRepo;
import budget_project.demo.services.CategoriesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepo categoriesRepository;

    @Override
    public CategoryResponseDTO createCategory(UUID userId, CategoryRequestDTO categoryRequest){
        if(categoryRequest.getCategoryName()==null || categoryRequest.getCategoryName().isEmpty())
         {throw new ResourceNotFoundException("No category name found!");}

        Categories category = new Categories();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setUserId(userId);
        if(categoryRequest.getType()!=null){
            category.setType(categoryRequest.getType());
        }
        Categories newCategory = categoriesRepository.save(category);
        CategoryResponseDTO.CategoryResponseDTOBuilder responseBuilder = CategoryResponseDTO.builder()
                .categoryId(newCategory.getCategoryId())
                .categoryName(newCategory.getCategoryName())
                .createdAt(newCategory.getCreatedAt());
        if(newCategory.getType()!=null){
            responseBuilder.type(newCategory.getType());
        }
        return responseBuilder.build();
    }

    @Override
    public SimpleMessage deleteCategory(UUID categoryId, UUID userId) {
        if (!categoriesRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category with id " + categoryId + " not found!");
        }
        boolean exists = categoriesRepository.existsByCategoryIdAndUserId(categoryId,userId);
        if(!exists) {
            throw new UnauthorizedException("You do not own this category");
        }
        categoriesRepository.deleteById(categoryId);
        return new SimpleMessage("Category deleted successfully!");
    }

    @Override
    public List<CategoryResponseDTO> getUserCategories(UUID userId){
         List<Categories> userCategories = categoriesRepository.findByUserId(userId);
         return userCategories.stream()
                 .map(category -> {
                     CategoryResponseDTO.CategoryResponseDTOBuilder responseBuilder = CategoryResponseDTO.builder()
                             .categoryId(category.getCategoryId())
                             .categoryName(category.getCategoryName())
                             .createdAt(category.getCreatedAt());
                     if(category.getType()!= null){
                         responseBuilder.type(category.getType());
                     }
                     return responseBuilder.build();
                 })
                 .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(UUID userId,UUID categoryId, CategoryRequestDTO requestUpdate) {
        Categories category = categoriesRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category belonging to user with id " + userId + " not found!"));
        if (!category.getUserId().equals(userId)) {
            throw new UnauthorizedException("You do not own this category.");
        }
        if(category.getCategoryName()!= null){
            category.setCategoryName(requestUpdate.getCategoryName());
        }
        if(category.getType()!=null){
            if(requestUpdate.getType()!=null) {
                category.setType(requestUpdate.getType());
            }
        }
        Categories updatedCategory = categoriesRepository.save(category);
        CategoryResponseDTO.CategoryResponseDTOBuilder response = CategoryResponseDTO.builder()
                .categoryId(updatedCategory.getCategoryId())
                .categoryName(updatedCategory.getCategoryName())
                .createdAt(updatedCategory.getCreatedAt())
                .lastUpdated(updatedCategory.getLastUpdated());
        if(updatedCategory.getType()!=null){
            response.type(updatedCategory.getType());
        }
        return response.build();

    }

}
