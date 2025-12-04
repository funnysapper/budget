package budget_project.demo.dtos;

import budget_project.demo.entities.TypeOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionDTO {
        private String transactionName;
        private BigDecimal amount;
        private UUID newCategoryId;
        private String description;
        private TypeOfTransaction type;
        private UUID transactionId;
    }


