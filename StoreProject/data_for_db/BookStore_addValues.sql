INSERT INTO book (name, author, publish_year, page_number, isbn, price, status, description, arrival_date)
VALUES 
	('Harry Potter', 'Joanne Rowling', 1996, 840, '42s3dsaf45', 48.0, 'IN_STOCK', 'Novell about young sorcerer', '2018-08-30'),
	('Code Completer', 'McConnell', 2004, 869, '0-7356-1967-0', 52.2, 'IN_STOCK', 'Helps to impove your coding skills', '2019-03-15'),
	('The Shining', 'Stephen King', 1977, 447, '978-0-385-12167-5', 18.65, 'IN_STOCK', 'First bestseller by author', '2020-04-05'),
	('The Dark Tower: The Gunslinger', 'Stephen King', 1982, 224, '978-0-937986-50-9', 17.35, 'IN_STOCK', 'First step for biggest novel by King', '2021-01-30'),
	('The Dark Tower II: The Drawing of the Three', 'Stephen King', 1987, 400, '978-0-937986-90-5', 29.90, 'IN_STOCK', 'Dark fantasy novell', '2021-01-30'),
	('The Dark Tower III: The Waste Lands', 'Stephen King', 1991, 512, '978-0-937986-17-2', 30.0, 'IN_STOCK', 'Dark fantasy novell', '2021-01-30'),
	('The Dark Tower IV: Wizard and Glass', 'Stephen King', 1997, 787, '978-1-880418-38-3', 32.0, 'IN_STOCK', 'Dark fantasy novell', '2021-02-04'),
	('The Dark Tower V: Wolves of the Calla', 'Stephen King', 2003, 714, '978-1-880-41856-7', 35.0, 'IN_STOCK', 'Dark fantasy novell', '2021-02-04'),
	('The Dark Tower VI: Song of Susannah', 'Stephen King', 2004, 432, '978-1-880-41859-8', 35.0, 'OUT_OF_STOCK', 'Dark fantasy novell', '2021-01-04'),
	('The Dark Tower VII: The Dark Tower', 'Stephen King', 2004, 835, '978-1-880-41862-8', 40.0, 'OUT_OF_STOCK', 'Dark fantasy novell', '2021-01-04'),
	('Head First Java', 'Kathy Sierra & Bert Bates', 2005, 720, '978-05960092058', 45.5, 'IN_STOCK', 'Great book if you novice in java', '2021-02-18'),
	('As I Lay Dying', 'William Faulkner', 1996, 288, '978-0679732259', 9.90, 'IN_STOCK', 'True 20th-century classic', '2021-02-18'),
	('Fahrenheit 451', 'Ray Bradbury', 1953, 256, '978-0-7432-4722-1 ', 23.0, 'OUT_OF_STOCK', 'World, where television rules and literature is on the brink of extinction', '2021-02-18'),
	('The Queen of Nothing', 'Holly Black', 2019, 308, '978-0-3163-1042-0', 33.5, 'IN_STOCK', 'Dark fantasy novell', '2021-02-18');
    
INSERT INTO ordering (customer_name, price, status, order_date, date_of_done)
VALUES 
	('Anton Vinogradov', 75.0, 'DONE', '2020-01-04', '2020-01-08'),
	('Denis', 64.15, 'DONE', '2020-01-04', '2020-01-08'),
	('Elena Zimova', 23.0, 'DONE', '2021-01-04', '2021-01-08'),
	('Pavel Sidorov', 23.0, 'CANCEL', '2021-03-18', NULL),
	('Alexander', 33.5, 'NEW', '2021-03-22', NULL),
	('Olga Slavina', 42.5, 'NEW', '2021-03-22', NULL);
    
INSERT INTO ordering_book
VALUES
	(1, 9),
	(1, 10),
	(2, 3),
	(2, 11),
	(3, 13),
	(4, 13),
	(5, 14),
	(6, 12),
	(6, 14);
    
INSERT INTO request (book_id, date, status)
VALUES 
	(2, '2021-01-18', 'CLOSED'),
	(4, '2021-01-21', 'CLOSED'),
	(13, '2021-03-16', 'OPEN'),
	(10, '2021-04-08', 'OPEN');