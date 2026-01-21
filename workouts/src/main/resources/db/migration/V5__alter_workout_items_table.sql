ALTER TABLE workout_items DROP COLUMN IF EXISTS exercise_name;
ALTER TABLE workout_items DROP COLUMN IF EXISTS exercise_id;

ALTER TABLE workout_items ADD COLUMN local_exercise_id BIGINT;

ALTER TABLE workout_items
    ADD CONSTRAINT fk_workout_items_local_exercise
        FOREIGN KEY (local_exercise_id)
            REFERENCES local_exercises (id);

CREATE INDEX idx_workout_items_local_exercise_id ON workout_items(local_exercise_id);