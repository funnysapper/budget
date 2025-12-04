package budget_project.demo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @Column(name="userId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    @Column(name="fullName")
    private String fullName;
    @Column(name="userName")
    private String userName;
    @Column(nullable = false, name = "passWord")
    private String password;
    @Column(unique=true, nullable=false, name="email")
    private String email;
    @Column(name="createdAt")
    private LocalDateTime createdAt;
    @Column(name="lastUpdated")
    private LocalDateTime lastUpdated;
    @PrePersist
    public void setCreatedAt(){
        this.createdAt= LocalDateTime.now();
    }
    @PreUpdate
    public void setLastUpdated(){this.lastUpdated = LocalDateTime.now();}
    @Enumerated(EnumType.STRING)
    private Role role;

}
