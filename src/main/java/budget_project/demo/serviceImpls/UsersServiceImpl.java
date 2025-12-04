package budget_project.demo.serviceImpls;

import budget_project.demo.dtos.CreateUser;
import budget_project.demo.dtos.UserResponse;
import budget_project.demo.entities.Users;
import budget_project.demo.exceptions.ResourceNotFoundException;
import budget_project.demo.exceptions.UserNotFoundException;
import budget_project.demo.miscellaneous.SimpleMessage;
import budget_project.demo.repositories.UsersRepo;
import budget_project.demo.services.UsersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SimpleMessage deleteUser(UUID id){
           if(!usersRepository.existsById(id)){
               throw new UserNotFoundException("User with id " +id+ " not found!");
           }
           usersRepository.deleteById(id);
           return new SimpleMessage("User deleted successfully!");
    }

    @Override
    public List<UserResponse> getAllUsers(){
        List<Users> users = usersRepository.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException("No users found!");
        }
        return usersRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .fullName(user.getFullName())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .createdAt(user.getCreatedAt())
                .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getProfile(UUID id){
        Optional<Users> optionalUser = usersRepository.findById(id);
        if(optionalUser.isEmpty()){
          throw new UserNotFoundException("User does not exist");
        }
        return UserResponse.builder()
                .fullName(optionalUser.get().getFullName())
                .userName(optionalUser.get().getUserName())
                .email(optionalUser.get().getEmail())
                .createdAt(optionalUser.get().getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, CreateUser update){
        Users registeredUser = usersRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User with id "+ id + "is not found"));
        if(update.getUserName()!=null && !update.getUserName().isEmpty()){
            registeredUser.setUserName(update.getUserName());
        }
        if(update.getFullName()!= null && !update.getFullName().isEmpty()){
            registeredUser.setFullName(update.getFullName());
        }
        if(update.getPassword()!= null && !update.getPassword().isEmpty()){
            registeredUser.setPassword(passwordEncoder.encode(update.getPassword()));
        }
        if(update.getEmail()!=null && !update.getEmail().isEmpty()){
            registeredUser.setEmail(update.getEmail());
        }
        Users updatedUser = usersRepository.save(registeredUser);
        return UserResponse.builder()
                .fullName(updatedUser.getFullName())
                .userName(updatedUser.getUserName())
                .email(updatedUser.getEmail())
                .lastUpdated(updatedUser.getLastUpdated())
                .build();
    }
}
