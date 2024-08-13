package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.repository.EmployeeAccountRepository;

import java.util.List;

@Service
public class EmployeeAccountService {
    private final EmployeeAccountRepository employeeAccountRepository;

    public EmployeeAccountService(EmployeeAccountRepository employeeAccountRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
    }

    public void addEmployeeAccount(EmployeeAccount employeeAccount) {
        employeeAccountRepository.save(employeeAccount);
    }

    public List<EmployeeAccount> getEmployeeAccounts() {
        return employeeAccountRepository.findAll();
    }
}
