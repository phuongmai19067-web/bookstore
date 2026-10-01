package fi.haagahelia.bookstore.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import fi.haagahelia.bookstore.model.AppUser;
import fi.haagahelia.bookstore.model.AppUserRepository;

// Lớp này nói cho Spring Security biết cách lấy user từ database
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository repository;

    public UserDetailServiceImpl(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong database theo username
        AppUser curruser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Chuyển AppUser thành đối tượng UserDetails mà Spring Security hiểu
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(curruser.getPassword());
        builder.roles(curruser.getRole());
        return builder.build();
    }
}
