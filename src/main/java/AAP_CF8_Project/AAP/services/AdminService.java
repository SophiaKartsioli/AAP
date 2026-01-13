package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Admin;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> findByUsername(String username);
    Admin save(Admin admin);
}
