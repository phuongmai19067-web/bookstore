package fi.haagahelia.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    // e) Dùng BCrypt để so khớp mật khẩu khi đăng nhập.
    // Spring Security sẽ tự động dùng UserDetailServiceImpl (có @Service)
    // cùng encoder này để xác thực user lấy từ database.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}