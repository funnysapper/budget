package budget_project.demo.serviceImpls;

import budget_project.demo.entities.Users;
import budget_project.demo.miscellaneous.CustomUserDetails;
import budget_project.demo.repositories.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepo usersRepository;
    public CustomUserDetailsService(UsersRepo usersRepository){this.usersRepository = usersRepository;}

    @Override
    public UserDetails loadUserByUsername(String email){
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
        return new CustomUserDetails(user);
    }
}
