package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "request")
public class Request extends AIdentity implements Serializable {
    static final long serialVersionUID = 4L;

    @EqualsAndHashCode.Include
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @EqualsAndHashCode.Include
    @Column(name = "date")
    private LocalDateTime requestDate = LocalDateTime.now();

    @Column(name = "request_count")
    private int requestCount = 1;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus = RequestStatus.OPEN;

    public Request() {

    }

    public Request(Book book) {
        this.book = book;
    }

    public Request(Book book, int requestCount) {
        this.book = book;
        this.requestCount = requestCount;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID=" + getId() +
                ", book_id=" + book.getId() +
                ", requestDate=" + requestDate.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd", Locale.ENGLISH)) +
                ", requestStatus=" + requestStatus +
                "}\n";
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

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}