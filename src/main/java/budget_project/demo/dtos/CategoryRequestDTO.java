package budget_project.demo.dtos;

import budget_project.demo.entities.TypeOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDTO {
    private String categoryName;
    private TypeOfTransaction type;

}
