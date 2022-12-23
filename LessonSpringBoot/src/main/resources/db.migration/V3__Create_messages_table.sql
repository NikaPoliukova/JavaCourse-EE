CREATE TABLE messages
(
    message_id     BIGSERIAL NOT NULL UNIQUE,
    source_user_id BIGINT    NOT NULL,
    target_user_id BIGINT    NOT NULL,
    message_content text ,
    created_at     TIMESTAMP NOT NULL DEFAULT now(),

    PRIMARY KEY (message_id)
);

INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (2, 1, 'Hello,user1!');
INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (1, 2, 'Hello,user2!');
INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (1, 3, 'Hello,user3!');
INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (1, 4, 'Hello,user4!');
INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (1, 5, 'Hello,user5!');
INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (1, 6, 'Hello,user6!');
INSERT INTO messages (source_user_id, target_user_id, message_content)
VALUES (3, 1, 'Hello,user7!');

