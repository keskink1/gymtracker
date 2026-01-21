CREATE TABLE local_exercises
(
    id           BIGINT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    muscle_group VARCHAR(100)
);

CREATE INDEX idx_local_exercises_name ON local_exercises (name);