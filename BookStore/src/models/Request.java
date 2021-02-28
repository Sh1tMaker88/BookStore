package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Request extends AIdentity implements Serializable {

    static final long serialVersionUID = 1L;
    private Book book;
    private LocalDateTime requestDate;
    private int requestCount;
    private RequestStatus requestStatus;

    public Request(Book book) {
        this.book = book;
        this.requestDate = LocalDateTime.now();
        requestStatus = RequestStatus.OPEN;
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
