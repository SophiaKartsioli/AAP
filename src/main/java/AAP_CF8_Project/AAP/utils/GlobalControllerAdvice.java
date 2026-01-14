package AAP_CF8_Project.AAP.utils;

import AAP_CF8_Project.AAP.domain.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final CurrentUser currentUser;

    public GlobalControllerAdvice(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @ModelAttribute("user")
    public User addUserToModel() {
        return currentUser.get();
    }

}
