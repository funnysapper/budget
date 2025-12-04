package budget_project.demo.dtos;

import budget_project.demo.entities.TypeOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private UUID categoryId;
    private String categoryName;
    private TypeOfTransaction type;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

}
