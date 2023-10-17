-- Create the Role table
CREATE TABLE Role
(
    role_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name        VARCHAR(255) NOT NULL UNIQUE,
    role_description TEXT         NOT NULL
);

-- Add the "Admin" and "User" roles
INSERT INTO Role (role_name, role_description)
VALUES ('Admin', 'Administrator role with full access.'),
       ('User', 'Regular user role.');

-- Create the User table
CREATE TABLE User
(
    user_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    gender        ENUM ('MALE', 'FEMALE', 'OTHER'),
    date_of_birth DATE         NOT NULL,
    age           INT          NOT NULL,
    firstname     VARCHAR(255) NOT NULL,
    lastname      VARCHAR(255) NOT NULL
);

-- Create the UserRole join table
CREATE TABLE User_Role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (role_id) REFERENCES Role (role_id)
);

-- Create the BlogPost table
CREATE TABLE BlogPost
(
    blog_post_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    title              VARCHAR(255) NOT NULL,
    content            TEXT         NOT NULL,
    creation_date      TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL,
    user_id            BIGINT,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);

-- Create the Comment table
CREATE TABLE Comment
(
    comment_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    content            TEXT      NOT NULL,
    creation_date      TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL,
    username           VARCHAR(255),
    blog_post_id       BIGINT,
    FOREIGN KEY (username) REFERENCES User (email),
    FOREIGN KEY (blog_post_id) REFERENCES BlogPost (blog_post_id)
);

-- Insert two new User records with roles (Admin and User)
INSERT INTO User (email, password, gender, date_of_birth, age, firstname, lastname)
VALUES ('admin@example.com', '$2a$10$at4zebsCGccyh03ODxLNIOMsKQwYfaMQlOGO9A3mZ3G8vROsv7eWC', 'MALE', '1990-01-01', 30,
        'Admin', 'User'),
       ('user@example.com', '$2a$10$l87KnXHTOrAPOVv7f4ZQnuEDv5IvtEe6eIoqN2zgZRYWfvBCUBFqS', 'FEMALE', '1995-05-05', 25,
        'Regular', 'User');

-- Assign roles to users
INSERT INTO User_Role (user_id, role_id)
SELECT user_id, role_id
FROM User,
     Role
WHERE email = 'admin@example.com'
  AND role_name = 'Admin';

INSERT INTO User_Role (user_id, role_id)
SELECT user_id, role_id
FROM User,
     Role
WHERE email = 'user@example.com'
  AND role_name = 'User';
