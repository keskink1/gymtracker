CREATE TABLE IF NOT EXISTS workouts
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    user_id
    BIGINT
    NOT
    NULL,
    workout_name
    VARCHAR
(
    255
) NOT NULL,
    category VARCHAR
(
    100
),
    description VARCHAR
(
    500
),
    status VARCHAR
(
    50
) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS workout_items
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    workout_id
    BIGINT
    NOT
    NULL,
    exercise_id
    BIGINT
    NOT
    NULL,
    sets
    INT
    NOT
    NULL,
    reps
    INT
    NOT
    NULL,
    weight
    DOUBLE
    DEFAULT
    0.0,

    CONSTRAINT
    fk_workout
    FOREIGN
    KEY
(
    workout_id
)
    REFERENCES workouts
(
    id
) ON DELETE CASCADE
    );