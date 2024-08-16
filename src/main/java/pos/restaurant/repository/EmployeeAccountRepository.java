package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.EmployeeAccount;

import java.util.Optional;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {
    boolean existsById(Long id);
}