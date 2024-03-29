-- CREATE DATABASE BookStore;

-- USE BookStore;

DROP TABLE IF EXISTS book, ordering, ordering_book, request;

CREATE TABLE book (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publish_year YEAR NOT NULL,
    status ENUM('IN_STOCK', 'OUT_OF_STOCK') NOT NULL,
    page_number SMALLINT NOT NULL,
    isbn  VARCHAR(20) UNIQUE NOT NULL,
    price DECIMAL(12, 2),
    description VARCHAR(256),
    arrival_date DATE NOT NULL,
    order_count INT DEFAULT 0
);

CREATE TABLE ordering (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    price DECIMAL (12, 2),
    status ENUM('NEW', 'DONE', 'CANCEL') DEFAULT 'NEW' NOT NULL,
    order_date DATETIME NOT NULL,
    date_of_done DATETIME
);

CREATE TABLE ordering_book (
    order_id BIGINT,
    book_id BIGINT,
    
    FOREIGN KEY (order_id) REFERENCES ordering (id),
    FOREIGN KEY (book_id) REFERENCES book (id)
);
CREATE INDEX order_id on ordering_book(order_id);
CREATE INDEX book_id on ordering_book(book_id);

CREATE TABLE request (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    status ENUM('OPEN', 'CLOSED') NOT NULL,
    date DATETIME NOT NULL,
    request_count INT DEFAULT 1,
    FOREIGN KEY (book_id) REFERENCES book (id)
);
CREATE INDEX book_id on request(book_id);


