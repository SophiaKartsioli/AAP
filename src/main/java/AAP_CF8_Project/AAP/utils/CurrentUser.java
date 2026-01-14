package AAP_CF8_Project.AAP.utils;


import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.security.CustomUserDetails;
import AAP_CF8_Project.AAP.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private final UserService userService;

    public CurrentUser(UserService userService) {
        this.userService = userService;
    }

    public User get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }
}
