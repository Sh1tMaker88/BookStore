package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Request extends AIdentity implements Serializable {

    static final long serialVersionUID = 3L;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(book, request.book) &&
                Objects.equals(requestDate, request.requestDate) &&
                requestStatus == request.requestStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, requestDate, requestStatus);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID=" + getId() +
                ", requestDate=" + requestDate.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd", Locale.ENGLISH)) +
                ", requestStatus=" + requestStatus +
                ", book=" + book.toString().replace("\n", "") +
                "} ";
    }
}
//requestDate=2021-3-02