-- CREATE DATABASE BookStore;

-- USE BookStore;

DROP TABLE IF EXISTS book, BookStore.order, ordering, order_book, request;

CREATE TABLE book (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publish_year YEAR NOT NULL,
    page_number SMALLINT NOT NULL,
    isbn  VARCHAR(20) UNIQUE NOT NULL,
    price DOUBLE(12, 2),
    status SET('IN_STOCK', 'OUT_OF_STOCK') NOT NULL,
    description VARCHAR(256),
    arrival_date DATE NOT NULL,
    order_count INT DEFAULT 0
);

CREATE TABLE ordering (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    price DECIMAL (12, 2),
    status SET('NEW', 'DONE', 'CANCEL') DEFAULT 'NEW' NOT NULL,
    order_date DATETIME NOT NULL,
    date_of_done DATETIME
);

CREATE TABLE ordering_book (
    order_id BIGINT,
    book_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES ordering (id),
    FOREIGN KEY (book_id) REFERENCES book (id)
);

CREATE TABLE request (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    date DATETIME NOT NULL,
    request_count INT DEFAULT 1,
    status SET('OPEN', 'CLOSED') NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id)
);



