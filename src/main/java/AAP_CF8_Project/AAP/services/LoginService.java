package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;

import java.util.Optional;

public interface LoginService {
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

}
