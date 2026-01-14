package AAP_CF8_Project.AAP.security;

import AAP_CF8_Project.AAP.domain.Admin;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.AdminService;
import AAP_CF8_Project.AAP.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final AdminService adminService;

    public CustomUserDetailsService(UserService userService,
                                    AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userService.findByUsername(username);
        if (user != null) {
            return new CustomUserDetails(
                    user.getUsername(),
                    user.getPasswordHash(),
                    Role.USER
            );
        }

        Admin admin = adminService.findByUsername(username).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(
                    admin.getUsername(),
                    admin.getPassword(),
                    Role.ADMIN
            );
        }

        throw new UsernameNotFoundException("User not found");
    }
}
