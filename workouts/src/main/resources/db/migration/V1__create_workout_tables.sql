CREATE TABLE workouts
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT       NOT NULL,
    workout_name VARCHAR(255) NOT NULL,
    category     VARCHAR(255),
    created_at   TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE workout_items
(
    id          BIGSERIAL PRIMARY KEY,
    workout_id  BIGINT,
    exercise_id BIGINT,
    sets        INTEGER          NOT NULL,
    reps        INTEGER          NOT NULL,
    weight      DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_workout_items_workout FOREIGN KEY (workout_id) REFERENCES workouts (id) ON DELETE CASCADE
);