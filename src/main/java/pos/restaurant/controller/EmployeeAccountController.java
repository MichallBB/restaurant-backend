package pos.restaurant.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.restaurant.DTO.ChangePinRequest;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.exceptions.OldPinIncorrect;
import pos.restaurant.exceptions.PinCannotBeTheSame;
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

    @GetMapping("/getByToken")
    public ResponseEntity<EmployeeAccountDto> getEmployeeAccountByTokenHeader(HttpServletRequest request){
        return ResponseEntity.ok(employeeAccountService.getEmployeeAccountByTokenHeader(request));
    }

    @PostMapping("/changePin")
    public ResponseEntity<EmployeeAccountDto> changePin(@RequestBody ChangePinRequest changePinRequest){
        try
        {
            return ResponseEntity.ok(employeeAccountService.changePin(
                    changePinRequest.getId(),
                    changePinRequest.getNewPin(),
                    changePinRequest.getOldPin()
            ));
        } catch (EmployeeAccountNotFound e){
            return ResponseEntity.status(500).build();
        } catch (OldPinIncorrect e){
            // old pin is not equal to the pin
            return ResponseEntity.status(411).build();
        } catch (IllegalArgumentException e){
            // is not digit or length is not 4
            return ResponseEntity.status(414).build();
        } catch (PinCannotBeTheSame e){
            // new pin cannot be the same
            return ResponseEntity.status(415).build();
        }

    }
}
