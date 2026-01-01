package com.library.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.application.dto.CreateBookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matcher.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BookControllerInegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateBookRequest createBookRequest;

    @BeforeEach
    void setUp(){
        createBookRequest = new CreateBookRequest();
        createBookRequest.setIsbn("9780306406157");
        createBookRequest.setTitle("Integration test book");
        createBookRequest.setAuthor("test author");
        createBookRequest.setPublicationYear(1999);
        createBookRequest.setPublisher("publisher");
        createBookRequest.setTotalCopies(5);
    }


    @Test
    void createBook_shouldReturnCreateBook() throws Exception{
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest))
        )
        .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn", is("9780306406157")))
                .andExpect(jsonPath("$.title", is("Integration test book")))
                .andExpect(jsonPath("$.author", is("test author")))
                .andExpect(jsonPath("$.totalCopies", is(5)))
                .andExpect(jsonPath("$.availableCopies", is(5)))
                .andExpect(jsonPath("$.publicationYear", is(1999)))
                .andExpect(jsonPath("$.publisher", is("publisher")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void createBook_withInvalidData_shouldReturnBadRequest() throws Exception{
        createBookRequest.setTitle("");

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.details.title", is("Title is required")));
    }

    @Test
    void getBook_shouldReturnBook() throws Exception{
        ResultActions createResult = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest))
        );

        String response = createResult.andReturn().getResponse().getContentAsString();
        Long bookId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookId.intValue())))
                .andExpect(jsonPath("$.title", is("Integration test book")));
    }

    @Test
    void getAllBooks_shouldReturnBooksList() throws Exception{
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest))
        );

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title", notNullValue()));
    }

    /*
    @Test
    void borrowBook_shouldSucceed() throws Exception {
        ResultActions createResult = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest))
        );
        Long bookId = objectMapper.readTree(
                createResult.andReturn().getResponse().getContentAsString()
        ).get("id").asLong();

        mockMvc.perform(post("/api/books/{id}/borrow", bookId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableCopies", is(2)));
    }*/

    @Test
    void searchBooks_shouldReturnMatchingBooks() throws Exception{
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest))
        );
        mockMvc.perform(get("/api/books/search")
                .param("keyword", "Integration")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
        .andExpect(jsonPath("$[0].title", containsString("Integration")));
    }
}