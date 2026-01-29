package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * This is the service implementation for managing authorization-related operations.
 *
 * It implements AnnouncementService to concrete business-logic for retrieving
 * a User by username or email during the login process. This service delegates
 * data access to UserRepository.
 */


@Service
public class LoginServiceImpl implements LoginService{
    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail);
    }
}
