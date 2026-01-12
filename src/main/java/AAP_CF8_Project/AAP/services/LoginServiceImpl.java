package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{
    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loginUser(String usernameOrEmail, String rawPassword) {
        Optional<User> userOpt =
                usernameOrEmail.contains("@")
                        ? userRepository.findByEmail(usernameOrEmail)
                        : userRepository.findByUsername(usernameOrEmail);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        if (!user.getPasswordHash().equals(rawPassword)) {
            return Optional.empty();
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return Optional.of(user);
    }
}
