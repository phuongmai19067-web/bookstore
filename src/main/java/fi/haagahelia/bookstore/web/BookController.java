package fi.haagahelia.bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.BookRepository;

@Controller
public class BookController {

    @Autowired
    private BookRepository repository;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // Bài 2: danh sách sách
    @GetMapping("/booklist")
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    // Bài 3: mở form thêm sách
    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    // Bài 3+4: lưu sách (thêm mới và sửa dùng chung)
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:booklist";
    }

    // Bài 3: xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId) {
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }

    // Bài 4: mở sách trong trang edit
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("book", repository.findById(bookId).get());
        return "editbook";
    }
}