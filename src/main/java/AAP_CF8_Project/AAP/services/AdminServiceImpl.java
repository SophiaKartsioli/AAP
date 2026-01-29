package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Admin;
import AAP_CF8_Project.AAP.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the service implementation for managing administration-related operations.
 *
 * It implements AdminsService to concrete business-logic for retrieving
 * and persisting Admin entity using AdminRepository.
 */

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
