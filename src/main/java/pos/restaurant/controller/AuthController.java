package pos.restaurant.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.restaurant.DTO.AuthResponseDto;
import pos.restaurant.DTO.LoginDTO;
import pos.restaurant.DTO.RegisterDTO;
import pos.restaurant.jwt.JwtService;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.repository.EmployeeAccountRepository;


import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    private EmployeeAccountRepository employeeAccountRepository;
    private JwtService jwtGenerator;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(EmployeeAccountRepository employeeAccountRepository,
                          JwtService jwtGenerator, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.employeeAccountRepository = employeeAccountRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getId(),
                        loginDto.getPinCode())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        EmployeeAccount userEntity = new EmployeeAccount();
        userEntity.setName(registerDto.getName());
        userEntity.setRole(registerDto.getRole());
        userEntity.setPin(passwordEncoder.encode("0000"));


        employeeAccountRepository.save(userEntity);

        return new ResponseEntity<>("Success, user registered!", HttpStatus.OK);
    }
}