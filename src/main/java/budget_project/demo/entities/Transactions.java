package budget_project.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transactions {

    @Id
    @Column(name="transactionId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;
    @Column(name="transactionName")
    private String transactionName;
    @Column(name="categoryId")
    private UUID categoryId;
    @Column(name="userId")
    private UUID userId;
    @Column(name="createdAt")
    private LocalDateTime createdAt;
    @Column(name="lastUpdated")
    private LocalDateTime lastUpdated;
    @Column(nullable = false, name="amount")
    private BigDecimal amount;
    @Column(name="description")
    private String description;
    @Column(nullable = false, name= "typeOfTransaction")
    private TypeOfTransaction type;
    @PrePersist
    public void setCreatedAt() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
    @PreUpdate
    public void setLastUpdated(){
        if(this.lastUpdated==null){
            this.lastUpdated=LocalDateTime.now();
        }
    }

}

