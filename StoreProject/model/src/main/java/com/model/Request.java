package com.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "request")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Request extends AIdentity implements Serializable {
    static final long serialVersionUID = 4L;

    @JsonIgnoreProperties({"orders", "request"})
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return requestCount == request.requestCount &&
                Objects.equals(book, request.book) &&
                Objects.equals(requestDate, request.requestDate) &&
                requestStatus == request.requestStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, requestDate, requestCount, requestStatus);
    }
}