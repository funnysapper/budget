package budget_project.demo.services;

import budget_project.demo.dtos.CreateUser;
import budget_project.demo.dtos.UserResponse;
import budget_project.demo.miscellaneous.SimpleMessage;

import java.util.List;
import java.util.UUID;


public interface UsersService {
    SimpleMessage deleteUser(UUID id);
    List<UserResponse> getAllUsers();
    UserResponse getProfile(UUID id);
    UserResponse updateUser(UUID id, CreateUser update);

}
