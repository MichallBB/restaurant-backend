package pos.restaurant.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.Role;
import pos.restaurant.service.EmployeeAccountService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/employeeAccount")
public class EmployeeAccountController {
    private final EmployeeAccountService employeeAccountService;

    public EmployeeAccountController(EmployeeAccountService employeeAccountService) {
        this.employeeAccountService = employeeAccountService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<HashMap<Role, List<EmployeeAccountDto>>> getAllEmployeeAccounts() {
        return ResponseEntity.ok(employeeAccountService.getAllEmployeeAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAccountDto> getEmployeeAccountById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(employeeAccountService.getEmployeeAccountById(id));
        } catch (EmployeeAccountNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }
}
