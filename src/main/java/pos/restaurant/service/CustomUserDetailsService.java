package pos.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pos.restaurant.models.EmployeeAccount;
import pos.restaurant.repository.EmployeeAccountRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private EmployeeAccountRepository userRepository;


    @Autowired
    public CustomUserDetailsService(EmployeeAccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        EmployeeAccount user = userRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        User springSecurityUser = new User(user.getUsername(), user.getPin(), user.getAuthorities());
        System.out.println(springSecurityUser.getUsername() + " " + springSecurityUser.getPassword() + " " + springSecurityUser.getAuthorities() + " " + springSecurityUser.isEnabled() + " " + springSecurityUser.isAccountNonExpired() + " " + springSecurityUser.isAccountNonLocked() + " " + springSecurityUser.isCredentialsNonExpired());
        return springSecurityUser;
    }

}