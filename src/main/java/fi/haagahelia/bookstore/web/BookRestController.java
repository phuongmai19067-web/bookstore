package fi.haagahelia.bookstore.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.BookRepository;

@RestController
public class BookRestController {

    @Autowired
    private BookRepository repository;

    // a) REST service: trả về TẤT CẢ sách dưới dạng JSON
    @GetMapping("/books")
    public Iterable<Book> getBooks() {
        return repository.findAll();
    }

    // b) REST service: trả về MỘT sách theo id (dùng path variable)
    @GetMapping("/books/{id}")
    public Optional<Book> getBook(@PathVariable("id") Long bookId) {
        return repository.findById(bookId);
    }
}