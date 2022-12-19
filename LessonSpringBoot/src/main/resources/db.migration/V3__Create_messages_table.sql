CREATE TABLE messages
(
    message_id            BIGSERIAL NOT NULL UNIQUE primary key,
    source_user_id BIGINT    NOT NULL,
    target_user_id BIGINT    NOT NULL,
    message_text text ,
    created_at     TIMESTAMP NOT NULL DEFAULT now(),

);