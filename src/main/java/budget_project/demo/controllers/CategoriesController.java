package budget_project.demo.controllers;

import budget_project.demo.dtos.CategoryRequestDTO;
import budget_project.demo.dtos.CategoryResponseDTO;
import budget_project.demo.miscellaneous.CustomUserDetails;
import budget_project.demo.miscellaneous.JwtFilter;
import budget_project.demo.miscellaneous.SimpleMessage;
import budget_project.demo.serviceImpls.CategoriesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesServiceImpl categoriesService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO request){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        CategoryResponseDTO response = categoriesService.createCategory(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<SimpleMessage> deleteCategory(@PathVariable UUID categoryId){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        SimpleMessage response = categoriesService.deleteCategory(userId, categoryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryRequestDTO request){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        CategoryResponseDTO response = categoriesService.updateCategory(userId,categoryId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getUserCategories(){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        List<CategoryResponseDTO> response = categoriesService.getUserCategories(userId);
        return ResponseEntity.ok(response);
    }
}
