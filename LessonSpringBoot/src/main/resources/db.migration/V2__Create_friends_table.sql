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
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (2, 1, 'APPROVED');
INSERT INTO friends (source_user_id, target_user_id,status)
VALUES (1, 3, 'APPROVED');
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (2, 3, 'APPROVED');
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (4, 3, 'APPROVED');
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (5, 3, 'APPROVED');
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (1, 4, 'APPROVED');
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (1, 5,'APPROVED');
INSERT INTO friends (source_user_id, target_user_id, status)
VALUES (1, 6, 'APPROVED');

