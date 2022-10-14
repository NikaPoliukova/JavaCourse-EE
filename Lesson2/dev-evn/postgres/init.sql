CREATE TABLE users
(
      username VARCHAR NOT NULL UNIQUE,
      password VARCHAR NOT NULL

);

INSERT INTO users (username, password)
VALUES ('user1', '123489');



