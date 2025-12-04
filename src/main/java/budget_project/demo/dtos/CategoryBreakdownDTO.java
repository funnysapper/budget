package budget_project.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBreakdownDTO {
    private UUID categoryId;
    private BigDecimal total;
    private double percentageOfTotal;


}
