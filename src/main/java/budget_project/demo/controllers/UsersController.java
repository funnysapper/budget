package budget_project.demo.controllers;

import budget_project.demo.dtos.CreateUser;
import budget_project.demo.dtos.UserResponse;
import budget_project.demo.miscellaneous.CustomUserDetails;
import budget_project.demo.miscellaneous.SimpleMessage;
import budget_project.demo.serviceImpls.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersServiceImpl usersService;

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody CreateUser update){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        UserResponse response = usersService.updateUser(userId, update);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        UserResponse response = usersService.getProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<SimpleMessage> deleteUser(){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        SimpleMessage response = usersService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> response = usersService.getAllUsers();
        return ResponseEntity.ok(response);
    }

}
