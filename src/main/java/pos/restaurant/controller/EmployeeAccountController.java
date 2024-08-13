package pos.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.service.EmployeeAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/employeeAccount")
public class EmployeeAccountController {
    private final EmployeeAccountService employeeAccountService;

    public EmployeeAccountController(EmployeeAccountService employeeAccountService) {
        this.employeeAccountService = employeeAccountService;
    }

    @PostMapping("/add")
    public void addEmployeeAccount(@RequestBody EmployeeAccount employeeAccount) {
        employeeAccountService.addEmployeeAccount(employeeAccount);
    }

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeAccount>> getEmployeeAccounts() {
        return ResponseEntity.ok(employeeAccountService.getEmployeeAccounts());
    }
}
