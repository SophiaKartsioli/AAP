package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.dto.LoginRequest;
import AAP_CF8_Project.AAP.services.LoginService;
import jakarta.servlet.http.HttpSession;
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

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
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

        Optional<User> userOpt =
                loginService.loginUser(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                );

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Invalid username or password");
            return "login_page";
        }

        session.setAttribute("loggedUser", userOpt.get());
        return "redirect:/profile/" + userOpt.get().getId();
    }



}
