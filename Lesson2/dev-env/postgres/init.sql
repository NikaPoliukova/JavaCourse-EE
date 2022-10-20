CREATE TABLE users
(
      username VARCHAR NOT NULL UNIQUE,
      password VARCHAR NOT NULL

);

INSERT INTO users (username, password)
VALUES ('user1', '123489');
INSERT INTO users (username, password)
VALUES ('user2', '12454489');
INSERT INTO users (username, password)
VALUES ('user3', '158543489');



