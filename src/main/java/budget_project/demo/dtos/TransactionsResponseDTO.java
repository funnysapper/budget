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
public class TransactionsResponseDTO {
    private BigDecimal amount;
    private String description;
    private TypeOfTransaction type;
    private UUID categoryId;
    private UUID transactionId;
    private String transactionName;
    private LocalDateTime createdAt;


}
