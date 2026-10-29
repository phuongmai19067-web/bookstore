package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.bookstore.model.AppUser;
import fi.haagahelia.bookstore.model.AppUserRepository;
import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.BookRepository;
import fi.haagahelia.bookstore.model.Category;
import fi.haagahelia.bookstore.model.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner bookstoreRunner(BookRepository repository,
            CategoryRepository categoryRepository,
            AppUserRepository userRepository) {
        return (args) -> {
            // Chỉ nạp dữ liệu mẫu khi database chưa có user nào
            // (tránh lỗi trùng key khi app khởi động lại trên database thật)
            if (userRepository.count() == 0) {

                // Tạo category
                Category fiction = new Category("Fiction");
                Category classic = new Category("Classic");
                categoryRepository.save(fiction);
                categoryRepository.save(classic);

                // Tạo sách và gán category
                repository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 19.90, fiction));
                repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 12.50, classic));

                // Tạo 2 user, hash mật khẩu BCrypt
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                userRepository.save(new AppUser("user",
                        encoder.encode("user"), "user@mjtea.fi", "USER"));
                userRepository.save(new AppUser("admin",
                        encoder.encode("admin"), "admin@mjtea.fi", "ADMIN"));
            }
        };
    }
}