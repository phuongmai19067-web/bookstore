package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	public CommandLineRunner bookstoreRunner(BookRepository repository, CategoryRepository categoryRepository) {
		return (args) -> {
			// Tạo category trước
			Category fiction = new Category("Fiction");
			Category classic = new Category("Classic");
			categoryRepository.save(fiction);
			categoryRepository.save(classic);

			// Tạo sách và gán category
			repository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 19.90, fiction));
			repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 12.50, classic));

			for (Book book : repository.findAll()) {
				System.out.println(book.getTitle());
			}
		};
	}
}