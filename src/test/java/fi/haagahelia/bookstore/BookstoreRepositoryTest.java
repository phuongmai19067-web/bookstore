package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.bookstore.model.AppUser;
import fi.haagahelia.bookstore.model.AppUserRepository;
import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.BookRepository;
import fi.haagahelia.bookstore.model.Category;
import fi.haagahelia.bookstore.model.CategoryRepository;

// Khởi động toàn bộ application context để test các repository
@SpringBootTest
public class BookstoreRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository userRepository;

    // ---------- BOOK REPOSITORY ----------

    // Create: lưu 1 sách mới, id khác null nghĩa là đã lưu thành công
    @Test
    public void createNewBook() {
        Book book = new Book("Test Book", "Test Author", 2020, "123-456", 10.0);
        bookRepository.save(book);
        assertThat(book.getId()).isNotNull();
    }

    // Search: tìm tất cả sách, phải có ít nhất 1 (dữ liệu mẫu)
    @Test
    public void findAllBooksShouldReturnList() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        assertThat(books).isNotEmpty();
    }

    // Delete: lưu rồi xóa, tìm lại phải không thấy
    @Test
    public void deleteBook() {
        Book book = new Book("To Delete", "Author", 2021, "999", 5.0);
        bookRepository.save(book);
        Long id = book.getId();
        bookRepository.deleteById(id);
        Optional<Book> deleted = bookRepository.findById(id);
        assertThat(deleted).isEmpty();
    }

    // ---------- CATEGORY REPOSITORY ----------

    @Test
    public void createNewCategory() {
        Category category = new Category("Test Category");
        categoryRepository.save(category);
        assertThat(category.getId()).isNotNull();
    }

    @Test
    public void deleteCategory() {
        Category category = new Category("Temp Category");
        categoryRepository.save(category);
        Long id = category.getId();
        categoryRepository.deleteById(id);
        Optional<Category> deleted = categoryRepository.findById(id);
        assertThat(deleted).isEmpty();
    }

    // ---------- APPUSER REPOSITORY ----------

    // Create + Search: lưu 1 user rồi tìm theo username
    @Test
    public void createAndFindUser() {
        AppUser user = new AppUser("testuser", "password123", "test@mjtea.fi", "USER");
        userRepository.save(user);

        Optional<AppUser> found = userRepository.findByUsername("testuser");
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@mjtea.fi");
    }

    @Test
    public void deleteUser() {
        AppUser user = new AppUser("deleteuser", "pass", "del@mjtea.fi", "USER");
        userRepository.save(user);
        Long id = user.getId();
        userRepository.deleteById(id);
        Optional<AppUser> deleted = userRepository.findById(id);
        assertThat(deleted).isEmpty();
    }
}