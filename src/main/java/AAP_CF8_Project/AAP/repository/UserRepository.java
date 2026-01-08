package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    
}
