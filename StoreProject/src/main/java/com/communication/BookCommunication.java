package com.communication;

import com.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BookCommunication {

    private RestTemplate restTemplate;
    private final String URL = "http://localhost:8080/books";

    @Autowired
    public BookCommunication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> getAllBooks() {
        ResponseEntity<List<Book>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {});
        List<Book> allBooks = responseEntity.getBody();
        return allBooks;
    }
}
