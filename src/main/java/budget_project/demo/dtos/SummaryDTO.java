package budget_project.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SummaryDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;

}
