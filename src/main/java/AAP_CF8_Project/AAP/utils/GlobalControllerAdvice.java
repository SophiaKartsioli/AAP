package AAP_CF8_Project.AAP.utils;

import AAP_CF8_Project.AAP.domain.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * This is the global controller for sharing common model attributes across all views.
 *
 * This class automatically adds the currently authorized user
 * to the model under the attitude name "user", making it accessible to
 * all controllers of Thymeleaf templates.
 */
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
