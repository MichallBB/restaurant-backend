package pos.restaurant.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pos.restaurant.DTO.EmployeeAccountDto;
import pos.restaurant.exceptions.EmployeeAccountNotFound;
import pos.restaurant.exceptions.OldPinIncorrect;
import pos.restaurant.exceptions.PinCannotBeTheSame;
import pos.restaurant.jwt.JwtService;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.models.Role;
import pos.restaurant.repository.EmployeeAccountRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class EmployeeAccountService {
    private final EmployeeAccountRepository employeeAccountRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public EmployeeAccountService(
            PasswordEncoder passwordEncoder,
            EmployeeAccountRepository employeeAccountRepository,
            JwtService jwtService
    ) {
        this.employeeAccountRepository = employeeAccountRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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

    public EmployeeAccountDto getEmployeeAccountByTokenHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken == null || !bearerToken.startsWith("Bearer ")){
            return null;
        }
        String token = bearerToken.substring(7);
        System.out.println(token);
        //decode token
        String claimsJws = jwtService.extractUsername(token);
        EmployeeAccount employeeAccount = employeeAccountRepository.findById(Long.valueOf(claimsJws)).orElseThrow(
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

    public List<EmployeeAccountDto> getAllEmployeesSortedByRole(){
        List<EmployeeAccount> employeeAccounts = employeeAccountRepository.findAllByOrderByRole();
        return employeeAccounts.stream().map(EmployeeAccountDto::toDto).toList();
    }

    public EmployeeAccountDto changePin(Long id, String newPin, String oldPin){
        if(newPin.equals(oldPin)){
            throw new PinCannotBeTheSame("New pin cannot be the same as old pin");
        }
        EmployeeAccount employeeAccount = employeeAccountRepository.findById(id).orElseThrow(
                () -> new EmployeeAccountNotFound("Employee account not found")
        );
        if (!passwordEncoder.matches(oldPin, employeeAccount.getPin())){
            throw new OldPinIncorrect("Old pin is incorrect");
        }
        if (newPin.length() != 4){
            throw new IllegalArgumentException("Pin must be 4 digits");
        }
        if (!newPin.trim().matches("\\d+")) {
            throw new IllegalArgumentException("Pin must be numeric");
        }
        employeeAccount.setPin(passwordEncoder.encode(newPin));
        employeeAccountRepository.save(employeeAccount);
        return EmployeeAccountDto.toDto(employeeAccount);
    }
}