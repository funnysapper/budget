package budget_project.demo.controllers;

import budget_project.demo.dtos.AuthResponse;
import budget_project.demo.dtos.CreateUser;
import budget_project.demo.dtos.LoginRequest;
import budget_project.demo.dtos.UserResponse;
import budget_project.demo.exceptions.InvalidEmailException;
import budget_project.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody CreateUser createUser){
            UserResponse response = authService.createUser(createUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
       AuthResponse authResponse = authService.login(loginRequest);
       return ResponseEntity.status(HttpStatus.OK).body(authResponse);
   }

}
