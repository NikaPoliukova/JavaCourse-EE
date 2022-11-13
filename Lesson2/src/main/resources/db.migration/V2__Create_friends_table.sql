CREATE TABLE friends
(
    id             BIGSERIAL NOT NULL UNIQUE,
    source_user_id BIGINT    NOT NULL,
    target_user_id BIGINT    NOT NULL,
    status         VARCHAR DEFAULT 'NEW',
    created_at     TIMESTAMP NOT NULL DEFAULT now(),
    update_at      TIMESTAMP NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);
INSERT INTO friends (source_user_id, target_user_id)
VALUES (2, 1);
INSERT INTO friends (source_user_id, target_user_id)
VALUES (1, 3);
