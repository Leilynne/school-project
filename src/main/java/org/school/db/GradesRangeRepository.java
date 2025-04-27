package org.school.db;

import org.school.model.GradesRange;
import org.jdbi.v3.core.Jdbi;

public class GradesRangeRepository {

    private final Jdbi jdbi;

    public GradesRangeRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createTable() {
        jdbi.useHandle(handle -> handle.execute("CREATE TABLE IF NOT EXISTS grades_ranges " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "range TEXT UNIQUE NOT NULL)"));
    }

    public int create(GradesRange gradeRange) {
        return jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO grades_ranges (range) VALUES (:range)")
                .bind("range", gradeRange.getRange())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(int.class)
                .one());
    }
}
