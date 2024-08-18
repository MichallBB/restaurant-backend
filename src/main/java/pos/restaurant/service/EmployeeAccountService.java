package pos.restaurant.service;

import org.springframework.stereotype.Service;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.Role;
import pos.restaurant.repository.EmployeeAccountRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class EmployeeAccountService {
    private final EmployeeAccountRepository employeeAccountRepository;

    public EmployeeAccountService(EmployeeAccountRepository employeeAccountRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
    }
    public EmployeeAccountDto getEmployeeAccountById(Long id){
        EmployeeAccount employeeAccount = employeeAccountRepository.findById(id).orElseThrow(
                () -> new EmployeeAccountNotFound("Employee account not found")
        );
        EmployeeAccountDto employeeAccountDto = new EmployeeAccountDto();
        employeeAccountDto.setId(employeeAccount.getId());
        employeeAccountDto.setName(employeeAccount.getName());
        employeeAccountDto.setRole(employeeAccount.getRole().toString());

        return employeeAccountDto;
    }

    public HashMap<Role, List<EmployeeAccountDto>> getAllEmployeeAccounts(){
        HashMap<Role, List<EmployeeAccountDto>> employeeAccounts = new HashMap<>();

        for (Role role : Role.values()) {
            List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAllByRole(role);

            List<EmployeeAccountDto> employeeAccountDtoList = employeeAccountList.stream().map(employeeAccount -> {
                EmployeeAccountDto employeeAccountDto = new EmployeeAccountDto();
                employeeAccountDto.setId(employeeAccount.getId());
                employeeAccountDto.setName(employeeAccount.getName());
                employeeAccountDto.setRole(employeeAccount.getRole().toString());
                return employeeAccountDto;
            }).toList();
            employeeAccounts.put(role, employeeAccountDtoList);
        }
        return employeeAccounts;
    }
}