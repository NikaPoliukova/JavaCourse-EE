CREATE TABLE invitations
(
    user_id   BIGINT    NOT NULL,
    friend_id     BIGINT    NOT NULL,
    createdDate TIMESTAMP NOT NULL DEFAULT now(),

    PRIMARY KEY (user_id, friend_id)
);