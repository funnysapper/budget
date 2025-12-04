package budget_project.demo.dtos;

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
public class UserResponse {
    private String fullName;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

}
