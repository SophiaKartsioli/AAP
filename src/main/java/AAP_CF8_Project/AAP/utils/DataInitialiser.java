package AAP_CF8_Project.AAP.utils;


import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitialiser implements CommandLineRunner {

    private final UserService userService;

    public DataInitialiser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if user already exists (optional)
        if (userService.findByUsername("test") == null) {
            User user = new User();
            user.setUsername("test");
            user.setEmail("test@example.com");
            // Ideally hash your password; for testing you can store plaintext
            user.setPasswordHash("1234");
            user.setBioText("This is the default admin user.");
            user.setCreatedDate(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());

            userService.save(user);

            System.out.println("Default admin user created: " + user);
        }
    }
}
