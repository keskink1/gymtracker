package com.keskin.workouts.repository;

import com.keskin.workouts.entity.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements IWorkoutRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Workout> workoutRowMapper = (rs, rowNum) ->
            new Workout(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("workout_name"),
                    rs.getString("category"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

    @Override
    public void save(Workout workout) {
        String sql = "INSERT INTO workouts (user_id, workout_name, category, created_at) VALUES (?, ? ,? ,?)";
        jdbcTemplate.update(
                sql,
                workout.getUserId(),
                workout.getWorkoutName(),
                workout.getCategory(),
                workout.getCreatedAt()
        );
    }

    @Override
    public List<Workout> findAll() {
        String sql = "SELECT * FROM workouts";
        return jdbcTemplate.query(sql, workoutRowMapper);
    }

    @Override
    public Workout findById(Long id) {
        String sql = "SELECT * FROM workouts WHERE id = ?";
        List<Workout> results = jdbcTemplate.query(sql, workoutRowMapper, id);

        return results.isEmpty() ? null : results.getFirst();
    }

    @Override
    public void update(Workout workout) {
        String sql = "UPDATE workouts SET workout_name = ? , category = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                workout.getWorkoutName(),
                workout.getCategory(),
                workout.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM workouts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
