CREATE TABLE exercises
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    muscle_group VARCHAR(100) NOT NULL,
    description  TEXT,
    CONSTRAINT uk_exercise_name UNIQUE (name)
);