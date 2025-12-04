package budget_project.demo.serviceImpls;

import budget_project.demo.dtos.AuthResponse;
import budget_project.demo.dtos.CreateUser;
import budget_project.demo.dtos.LoginRequest;
import budget_project.demo.dtos.UserResponse;
import budget_project.demo.entities.Role;
import budget_project.demo.entities.Users;
import budget_project.demo.exceptions.EmailRequiredException;
import budget_project.demo.exceptions.InvalidEmailException;
import budget_project.demo.exceptions.PasswordRequiredException;
import budget_project.demo.exceptions.ResourceNotFoundException;
import budget_project.demo.mappers.UsersMapper;
import budget_project.demo.miscellaneous.JwtUtils;
import budget_project.demo.repositories.UsersRepo;
import budget_project.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsersRepo usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public UserResponse createUser(CreateUser request){
        if(usersRepository.existsByEmail(request.getEmail())){
            throw new InvalidEmailException("Email already exists");
        }
        if(request.getEmail()== null || request.getEmail().isEmpty()){
            throw new EmailRequiredException("Email is required!");
        }
        if(request.getPassword()==null || request.getPassword().isEmpty()){
            throw new PasswordRequiredException("Password is required!");
        }
        if(request.getUserName()==null || request.getUserName().isEmpty()){
            throw new ResourceNotFoundException("Username is required!");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        Users newUser = new Users();
        newUser.setFullName(request.getFullName());
        newUser.setUserName(request.getUserName());
        newUser.setPassword(hashedPassword);
        newUser.setEmail(request.getEmail());
        newUser.setRole(Role.USER);
        Users savedUser = usersRepository.save(newUser);

        UserResponse dto =  UsersMapper.toDto(savedUser);
        return dto;

    }

    @Override
    public AuthResponse login(LoginRequest request){
        if(request.getEmail()==null || request.getEmail().isEmpty()){
            throw new EmailRequiredException("Enter your email address");
        }

        Users user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new InvalidEmailException("Email not found"));

        String token = jwtUtils.generateToken(request.getEmail(), user.getUserId());
        return AuthResponse.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}
