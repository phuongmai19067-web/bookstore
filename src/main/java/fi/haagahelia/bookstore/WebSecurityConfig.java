package fi.haagahelia.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// Cho phép dùng @PreAuthorize trên method (để chặn Delete theo role)
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    // Quy định URL nào cần đăng nhập, cấu hình login & logout
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép tải CSS kể cả khi chưa đăng nhập
                .requestMatchers("/css/**").permitAll()
                // Mọi URL còn lại bắt buộc đăng nhập
                .anyRequest().authenticated()
            )
            // Dùng form login mặc định của Spring, đăng nhập xong về /booklist
            .formLogin(formlogin -> formlogin
                .defaultSuccessUrl("/booklist", true).permitAll()
            )
            // Cho phép logout
            .logout(logout -> logout.permitAll());
        return http.build();
    }

    // Tạo 2 user lưu trong bộ nhớ (in-memory)
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();

        // Bộ mã hóa mật khẩu
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // user thường: username=user, password=user, role USER
        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();
        users.add(user1);

        // admin: username=admin, password=admin, role USER + ADMIN
        UserDetails user2 = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        users.add(user2);

        return new InMemoryUserDetailsManager(users);
    }
}