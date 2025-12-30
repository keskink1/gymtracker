CREATE TABLE app_users
(
    user_id    BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    height     DOUBLE PRECISION,
    weight     DOUBLE PRECISION,
    age        INTEGER,
    is_active  BOOLEAN DEFAULT TRUE,
    CONSTRAINT uk_app_users_email UNIQUE (email)
);