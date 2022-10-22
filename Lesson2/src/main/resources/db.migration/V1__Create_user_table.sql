CREATE TABLE users
(
    id         BIGSERIAL NOT NULL UNIQUE,
    username   VARCHAR   NOT NULL UNIQUE,
    password   VARCHAR   NOT NULL,
    createdDate TIMESTAMP NOT NULL DEFAULT now()
);


INSERT INTO users ( username, password) VALUES ('user1', '123489');
INSERT INTO users ( username, password) VALUES ('user2', '12454489');
INSERT INTO users ( username, password) VALUES ('user3', '158543489');
