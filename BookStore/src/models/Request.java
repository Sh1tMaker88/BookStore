package models;

import java.time.LocalDateTime;

public class Request extends AIdentity{
    private Book book;
    private LocalDateTime requestDate;
    private int requestCount;

    public Request(Book book) {
        this.book = book;
        this.requestDate = LocalDateTime.now();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id= " + getId() +
                ", book=" + book +
                ", requestDate=" + requestDate +
                "} ";
    }
}
