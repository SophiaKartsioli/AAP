package AAP_CF8_Project.AAP.controller;


import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.security.CustomUserDetails;
import AAP_CF8_Project.AAP.security.Role;
import AAP_CF8_Project.AAP.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * This controller is responsible for operations related of the users.
 * It handles listing the new users, creating new ones, editing existing ones and delete as well.
 * For procotion also checks if the new user is listed.
 *
 */
@Controller()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @GetMapping()
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());

        Iterable<User> users = userService.findAll();

        System.out.println("USERS FOUND:");
        users.forEach(System.out::println);

        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("newuser", new User());
        return "user_form";
    }


    @PostMapping()
    public String saveUser(@ModelAttribute("newuser") User formUser) {
        LocalDateTime now = LocalDateTime.now();

        if (formUser.getId() != 0 && userService.existsById(formUser.getId())) {
            System.out.println("=== Edit POST /users HIT ===");

            User existingUser = userService.findById(formUser.getId());
            existingUser.setUsername(formUser.getUsername());
            existingUser.setEmail(formUser.getEmail());
            if (!formUser.getPasswordHash().isEmpty()) {
                existingUser.setPasswordHash(passwordEncoder.encode(formUser.getPasswordHash()));
            }
            existingUser.setBioText(formUser.getBioText());
            existingUser.setLastLogin(now); // optional
            userService.save(existingUser);
        } else {
            System.out.println("=== Create POST /users HIT ===");

            formUser.setPasswordHash(passwordEncoder.encode(formUser.getPasswordHash()));
            formUser.setCreatedDate(now);
            formUser.setLastLogin(now);
            formUser.setRole(Role.USER);
            userService.save(formUser);
        }

        authenticateUser(formUser);


        return "redirect:/profile/" + formUser.getId();

    }


    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/users"; // optional: user not found
        }
        model.addAttribute("user", user); // Thymeleaf will prefill fields
        return "user_form";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    private void authenticateUser(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPasswordHash(),
                user.getRole()
        );

        var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }


}
