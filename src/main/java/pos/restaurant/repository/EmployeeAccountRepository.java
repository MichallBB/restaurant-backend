package pos.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.restaurant.models.EmployeeAccount;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {
}