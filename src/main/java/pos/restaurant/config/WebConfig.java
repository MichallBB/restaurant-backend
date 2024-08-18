package pos.restaurant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pos.restaurant.jwt.JWTAuthenticationFilter;
import pos.restaurant.jwt.JwtAuthEntryPoint;
import pos.restaurant.jwt.JwtService;
import pos.restaurant.models.Role;
import pos.restaurant.service.CustomUserDetailsService;
import pos.restaurant.service.EmployeeAccountService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private JwtAuthEntryPoint authEntryPoint;
    private CustomUserDetailsService userDetailsService;

    private JwtService jwtService;
    @Autowired
    public WebConfig(JwtAuthEntryPoint authEntryPoint, CustomUserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.jwtService = jwtService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
//                                .requestMatchers(HttpMethod.POST,"/api/auth/register").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/auth/register").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
//                                .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/employeeAccount/getAll").permitAll()
                                .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(new JWTAuthenticationFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Dla wszystkich ścieżek
                .allowedOrigins("http://localhost:4200") // Zezwól na żądania z tego origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Zezwólone metody HTTP
                .allowedHeaders("*") // Zezwól na wszystkie nagłówki
                .allowCredentials(true); // Zezwól na przesyłanie ciasteczek
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
