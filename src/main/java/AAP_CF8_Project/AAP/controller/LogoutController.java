package AAP_CF8_Project.AAP.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * The Controller which is responsible for the user log out operation.
 * When the user wishes to log out from their account, the controller clears the session and get redirected to the login page.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping()
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
