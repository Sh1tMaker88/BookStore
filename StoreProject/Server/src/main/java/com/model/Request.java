package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Request extends AIdentity implements Serializable {

    static final long serialVersionUID = 3L;
    private Long book_id;
    private LocalDateTime requestDate = LocalDateTime.now();
    private int requestCount = 1;
    private RequestStatus requestStatus = RequestStatus.OPEN;

    public Request() {

    }

    public Request(Long book_id) {
        this.book_id = book_id;
    }

    public Request(Long book_id, int requestCount) {
        this.book_id = book_id;
        this.requestCount = requestCount;
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