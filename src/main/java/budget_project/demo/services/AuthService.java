package budget_project.demo.services;

import budget_project.demo.dtos.AuthResponse;
import budget_project.demo.dtos.CreateUser;
import budget_project.demo.dtos.LoginRequest;
import budget_project.demo.dtos.UserResponse;

public interface AuthService {
    UserResponse createUser(CreateUser request);
    AuthResponse login(LoginRequest login);

}
