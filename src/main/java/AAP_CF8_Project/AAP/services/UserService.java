package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;


public interface UserService {
    Iterable<User> findAll();

    User findById(int id);
    User findByUsername(String username);
    void save(User user);
    void deleteById(int id);
    boolean existsById(int id);
}
