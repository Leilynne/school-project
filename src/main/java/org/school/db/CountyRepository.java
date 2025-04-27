package org.school.db;

import org.school.model.County;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountyRepository {

    private final Jdbi jdbi;

    public CountyRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public void createTable() {
        jdbi.useHandle(handle -> handle.execute("CREATE TABLE IF NOT EXISTS counties (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL)"));
    }

    public int create(County county) {
        return jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO counties (name) VALUES (:name)")
                .bind("name", county.getName())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(int.class)
                .one());
    }

    public Map<String, Double> getAverageStudentsByCounties(String... counties) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT c.name AS county_name, AVG(sm.students) AS avg_students " +
                                        "FROM school_metrics sm " +
                                        "JOIN schools s ON sm.school_id = s.id " +
                                        "JOIN counties c ON s.county_id = c.id " +
                                        "WHERE c.name IN (<counties>) " +
                                        "GROUP BY c.name"
                        )
                        .bindList("counties", List.of(counties))
                        .mapToMap()
                        .list()
                        .stream()
                        .collect(Collectors.toMap(
                                row -> (String) row.get("county_name"),
                                row -> (Double) row.get("avg_students")
                        ))
        );
    }
}
