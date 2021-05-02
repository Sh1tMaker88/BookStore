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
    @OneToOne (cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "book_id")
    private Book bookId;

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

    public Request(Long bookId) {
        this.bookId = bookId;
    }

    public Request(Long bookId, int requestCount) {
        this.bookId = bookId;
        this.requestCount = requestCount;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID=" + getId() +
                ", book_id=" + bookId +
                ", requestDate=" + requestDate.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd", Locale.ENGLISH)) +
                ", requestStatus=" + requestStatus +
                "}\n";
    }
}