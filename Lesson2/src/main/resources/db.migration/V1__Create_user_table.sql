CREATE TABLE users
(
    user_id     BIGSERIAL NOT NULL UNIQUE,
    username    VARCHAR   NOT NULL UNIQUE,
    password    VARCHAR   NOT NULL,
    createdDate TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO users ( username, password)
VALUES ('nika', '$2a$12$4k0YIr9v/ckNISWo7A3JVeZfo.YMM9Q6FBiK.VBexru3UYhYjSGWa');
INSERT INTO users ( username, password)
VALUES ('user2', '12454489');
INSERT INTO users ( username, password)
VALUES ('user3', '158543489');
