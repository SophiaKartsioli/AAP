package AAP_CF8_Project.AAP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping()
    public String home() {
        return "home_page"; // return home-page.html from templates
    }
}
