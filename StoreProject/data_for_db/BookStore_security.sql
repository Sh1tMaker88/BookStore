DROP TABLE IF EXISTS users, role, roles, users_role, authorities;

CREATE TABLE users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled TINYINT(1) DEFAULT 1
);
CREATE UNIQUE INDEX login ON users(username);

/* CREATE TABLE authorities (
	username VARCHAR(15),
    authority VARCHAR(25),
    FOREIGN KEY (username) REFERENCES users(username)
); */

CREATE TABLE role (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(15) NOT NULL
);
CREATE UNIQUE INDEX role ON role(role_name);

CREATE TABLE users_role (
	username VARCHAR(25),
    role_name VARCHAR(15),
    
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (role_name) REFERENCES role(role_name)
);
CREATE INDEX username ON users_role(username);
CREATE INDEX role_name ON users_role(role_name);

INSERT INTO users (username, password)
VALUES ('admin_name', '$2y$10$rZF.PX./ojG041lxwYXJjekb40TgjhV42xEJ2ZRs8FiW4oy2qW9Ee'), 
('user_name', '$2y$10$LgbojSqGUf.RrGzztUFDm.Z4q.cEA423OQQu.4sptnSr05G1eRW9e');

INSERT INTO role (role_name)
VALUES ('ADMIN'), ('USER');

INSERT INTO users_role (username, role_name)
VALUES ('admin_name', 'ADMIN'), ('admin_name', 'USER'), ('user_name', 'USER');

/* INSERT INTO authorities (username, authority)
VALUES ('admin_name', 'ROLE_ADMIN'), ('user_name', 'ROLE_USER'); */
