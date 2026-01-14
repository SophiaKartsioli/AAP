package AAP_CF8_Project.AAP.utils;


import AAP_CF8_Project.AAP.domain.Admin;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.security.Role;
import AAP_CF8_Project.AAP.services.AdminService;
import AAP_CF8_Project.AAP.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitialiser implements CommandLineRunner {

    private final UserService userService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    public DataInitialiser(UserService userService, AdminService adminService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if user already exists (optional)
        if (userService.findByUsername("test") == null) {
            User user = new User();
            user.setUsername("test");
            user.setEmail("test@example.com");
            // Ideally hash your password; for testing you can store plaintext
            user.setPasswordHash(passwordEncoder.encode("1234"));;
            user.setBioText("This is the default admin user.");
            user.setCreatedDate(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            //user.setRole("ROLE_USER");

            userService.save(user);

            System.out.println("Default user created: " + user);

        }

        if (adminService.findByUsername("admin").isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));; // plaintext for simplicity
            adminService.save(admin);

            System.out.println("Default admin  created: " + admin);
        }
    }
}
