package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookstoreRunner(BookRepository repository) {
		return (args) -> {
			repository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 19.90));
			repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 12.50));

			for (Book book : repository.findAll()) {
				System.out.println(book.getTitle());
			}
		};
	}
}