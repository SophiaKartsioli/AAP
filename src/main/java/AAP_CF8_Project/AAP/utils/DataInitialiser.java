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

/**
 * This is utility responsible for initializing default application data.
 *
 *  This class runs at application startup and creates predefined User and Admin
 *  accounts if they do not already exist in the database. It is primarily intended
 *  for development and testing purposes.
 */
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

        if (userService.findByUsername("test") == null) {
            User user = new User();
            user.setUsername("test");
            user.setEmail("test@example.com");

            user.setPasswordHash(passwordEncoder.encode("1234"));
            user.setBioText("This is the default admin user.");
            user.setCreatedDate(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());


            userService.save(user);

            System.out.println("Default user created: " + user);

        }

        if (adminService.findByUsername("admin").isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            adminService.save(admin);

            System.out.println("Default admin  created: " + admin);
        }
    }
}
