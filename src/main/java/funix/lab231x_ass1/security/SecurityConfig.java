package funix.lab231x_ass1.security;

import funix.lab231x_ass1.model.User;
import funix.lab231x_ass1.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    // Cấu hình SecurityFilterChain cho các endpoint
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login", "/register") // Bỏ qua CSRF cho trang login và register
                )
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/register", "/css/**", "/images/**", "/static/**", "/header.html").permitAll() // Cho phép không cần đăng nhập
                        .requestMatchers("/makequiz").hasRole("TEACHER") // Chỉ cho phép truy cập khi có role TEACHER
                        .requestMatchers("/takequiz").hasRole("STUDENT")// Chỉ cho phép truy cập khi có role STUDENT
                        .anyRequest().authenticated() // Các request khác yêu cầu đăng nhập
                )
                .formLogin(form -> form
                        .loginPage("/login") // Trang login tùy chỉnh
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/Home", true) // Đường dẫn sau khi login thành công
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Đường dẫn logout
                        .logoutSuccessUrl("/login") // Chuyển hướng sau khi logout
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        // Sử dụng NoOpPasswordEncoder.getInstance() thay vì khởi tạo trực tiếp
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Lấy user từ cơ sở dữ liệu, ví dụ: repository.findByUsername(username)
            User user = userRepository.findByUsername(username);
            if (user != null) {
                // So sánh mật khẩu trực tiếp từ database, không mã hóa
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword()) // Mật khẩu không mã hóa
                        .roles(user.getRole().name()) // Thiết lập role cho user
                        .build();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}