create table image
(
    image_id   BIGSERIAL NOT NULL UNIQUE,
    image_name varchar,
    user_id BIGINT,

    PRIMARY KEY (image_id)
);