package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Request extends AIdentity implements Serializable {

    static final long serialVersionUID = 3L;
    private Long book_id;
    private LocalDateTime requestDate = LocalDateTime.now();
    private int requestCount = 1;
    private RequestStatus requestStatus = RequestStatus.OPEN;

    public Request() {

    }

    public Request(Long book_id, int requestCount) {
        this.book_id = book_id;
        this.requestCount = requestCount;
    }

    public Request(Long book_id) {
        this.book_id = book_id;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
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

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(book_id, request.book_id) &&
                Objects.equals(requestDate, request.requestDate) &&
                requestStatus == request.requestStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, requestDate, requestStatus);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID=" + getId() +
                ", book_id=" + book_id +
                ", requestDate=" + requestDate.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd", Locale.ENGLISH)) +
                ", requestStatus=" + requestStatus +
                "}\n";
    }
}
//requestDate=2021-3-02