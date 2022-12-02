CREATE TABLE users
(
    user_id     BIGSERIAL NOT NULL UNIQUE,
    username    VARCHAR   NOT NULL UNIQUE,
    password    VARCHAR   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO users ( username, password)
VALUES ('nika', '$2a$12$4k0YIr9v/ckNISWo7A3JVeZfo.YMM9Q6FBiK.VBexru3UYhYjSGWa');
INSERT INTO users ( username, password)
VALUES ('user2', '$2a$12$WsnlLBmoEmgGzwkx.2sbF.Kkv28AzH2GOx6ZJsDdlBoMidv4R0ZDu');
INSERT INTO users ( username, password)
VALUES ('user3', '$2a$12$J6ugEhxhQTfDcIO5uXq14Oi5ecplwx9qTlQceLwysnWqJho1gxPVC');
