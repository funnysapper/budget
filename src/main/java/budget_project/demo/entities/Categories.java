package budget_project.demo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categories {

    @Id
    @Column(name="categoryId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;
    @Column(nullable = false, name="categoryName")
    private String categoryName;
    @Column(name="typeOfTransaction")
    private TypeOfTransaction type;
    @Column(name="userId")
    private UUID userId;
    @Column(name="createdAt")
    private LocalDateTime createdAt;
    @Column(name="latUpdated")
    private LocalDateTime lastUpdated;
    @PrePersist
    public void setCreatedAt(){
        this.createdAt= LocalDateTime.now();
    }
    @PreUpdate
    public void setLastUpdated(){this.lastUpdated = LocalDateTime.now();}

}
