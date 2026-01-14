package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.dto.LoginRequest;
import AAP_CF8_Project.AAP.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller()
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(LoginService loginService,PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showLogin(Model model) {
        model.addAttribute("loginUser", new LoginRequest());
        return "login_page";
    }

    @PostMapping()
    public String login(
            @ModelAttribute("loginUser") LoginRequest loginRequest,
            Model model,
            HttpSession session
    ) {

        Optional<User> userOpt = loginService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail());

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Invalid username or password");
            return "login_page";
        }
        User user = userOpt.get();

        // Use PasswordEncoder to compare raw password with encoded password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            model.addAttribute("error", "Invalid username or password");
            return "login_page";
        }

        session.setAttribute("loggedUser", userOpt.get());
        return "redirect:/profile/" + userOpt.get().getId();
    }



}
