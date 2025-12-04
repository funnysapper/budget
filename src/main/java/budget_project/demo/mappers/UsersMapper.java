package budget_project.demo.mappers;

import budget_project.demo.dtos.UserResponse;
import budget_project.demo.entities.Users;

public class UsersMapper {

    public static UserResponse toDto(Users user){
         return UserResponse.builder()
                 .userName(user.getUserName())
                 .fullName(user.getFullName())
                 .email(user.getEmail())
                 .createdAt(user.getCreatedAt())
                 .build() ;
    }


}
