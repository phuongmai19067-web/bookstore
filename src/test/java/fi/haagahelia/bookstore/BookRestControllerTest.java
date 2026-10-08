package fi.haagahelia.bookstore;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

// Khởi động toàn bộ application context + MockMvc để test tầng web (REST)
@SpringBootTest
@AutoConfigureMockMvc
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test: GET /books phải trả về 200 và JSON có chứa tên sách mẫu.
    // .with(user(...)) giả lập một user đã đăng nhập (app yêu cầu authentication).
    @Test
    public void getAllBooksShouldReturnBooks() throws Exception {
        this.mockMvc.perform(get("/books").with(user("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hemingway")));
    }

    // Test: GET /books/1 phải trả về 200 và JSON của 1 quyển sách
    @Test
    public void getBookByIdShouldReturnBook() throws Exception {
        this.mockMvc.perform(get("/books/1").with(user("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }
}