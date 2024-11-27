package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.Role;

import java.util.List;
import java.util.Optional;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {
    boolean existsById(Long id);
    List<EmployeeAccount> findAllByRole(Role role);

    List<EmployeeAccount> findAllByOrderByRole();
}