package org.school.db;

import org.jdbi.v3.core.Jdbi;
import org.school.model.School;
import org.school.model.SchoolMetrics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SchoolRepository {
    private final Jdbi jdbi;

    public SchoolRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createTable() {
        jdbi.useHandle(handle -> handle.createUpdate(
                "CREATE TABLE IF NOT EXISTS schools " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "county_id INTEGER NOT NULL," +
                        "district INTEGER NOT NULL," +
                        "grades_range_id INTEGER NOT NULL," +
                        "FOREIGN KEY (county_id) REFERENCES counties (id)," +
                        "FOREIGN KEY (grades_range_id) REFERENCES grades_ranges (id))"
        ).execute());
        jdbi.useHandle(handle -> handle.createUpdate(
                "CREATE TABLE IF NOT EXISTS school_metrics " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "school_id INTEGER UNIQUE NOT NULL, " +
                        "students INTEGER NOT NULL DEFAULT 0," +
                        "teachers REAL NOT NULL DEFAULT 0.0," +
                        "calworks REAL NOT NULL DEFAULT 0.0," +
                        "lunch REAL NOT NULL DEFAULT 0.0," +
                        "computer INTEGER NOT NULL DEFAULT 0," +
                        "expenditure REAL NOT NULL DEFAULT 0.0," +
                        "income REAL NOT NULL DEFAULT 0.0," +
                        "english REAL NOT NULL DEFAULT 0.0," +
                        "read REAL NOT NULL DEFAULT 0.0," +
                        "math REAL NOT NULL DEFAULT 0.0," +
                        "FOREIGN KEY (school_id) REFERENCES schools (id))"
        ).execute());
    }

    public void createSchoolWithMetrics(School school, SchoolMetrics metrics) {
        int schoolId = createSchool(school);
        createSchoolMetrics(metrics, schoolId);
    }

    private int createSchool(School school) {
        return jdbi.withHandle(handle ->
                handle.createUpdate(
                                "INSERT INTO schools (name, county_id, district, grades_range_id) " +
                                        "VALUES (:name, :countyId, :district, :gradesRangeId)"
                        )
                        .bind("name", school.getName())
                        .bind("countyId", school.getCountyId())
                        .bind("district", school.getDistrict())
                        .bind("gradesRangeId", school.getGradesRangeId())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }

    private void createSchoolMetrics(SchoolMetrics metrics, int schoolId) {
        jdbi.useHandle(handle -> handle.createUpdate(
                        "INSERT INTO school_metrics " +
                                "(school_id, students, teachers, calworks, lunch, computer, expenditure, income, english, read, math) " +
                                "VALUES (:schoolId, :students, :teachers, :calworks, :lunch, :computer, :expenditure, :income, :english, :read, :math)"
                )
                .bind("schoolId", schoolId)
                .bind("students", metrics.getStudents())
                .bind("teachers", metrics.getTeachers())
                .bind("calworks", metrics.getCalworks())
                .bind("lunch", metrics.getLunch())
                .bind("computer", metrics.getComputer())
                .bind("expenditure", metrics.getExpenditure())
                .bind("income", metrics.getIncome())
                .bind("english", metrics.getEnglish())
                .bind("read", metrics.getRead())
                .bind("math", metrics.getMath())
                .execute());
    }

    public Map<String, Double> findAverageExpenditureByCounties(String... counties) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT c.name AS county_name, AVG(sm.expenditure) AS avg_expenditure " +
                                        "FROM school_metrics sm " +
                                        "JOIN schools s ON sm.school_id = s.id " +
                                        "JOIN counties c ON s.county_id = c.id " +
                                        "WHERE c.name IN (<counties>) AND sm.expenditure > 10 " +
                                        "GROUP BY c.name"
                        )
                        .bindList("counties", List.of(counties))
                        .mapToMap()
                        .list()
                        .stream()
                        .collect(Collectors.toMap(
                                row -> (String) row.get("county_name"),
                                row -> (Double) row.get("avg_expenditure")
                        ))
        );
    }

    public School getTopSchoolByMath(int minStudents, int maxStudents) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT s.* FROM school_metrics sm " +
                                        "JOIN schools s ON sm.school_id = s.id " +
                                        "WHERE sm.students BETWEEN :minStudents AND :maxStudents " +
                                        "ORDER BY sm.math DESC " +
                                        "LIMIT 1"
                        )
                        .bind("minStudents", minStudents)
                        .bind("maxStudents", maxStudents)
                        .mapToBean(School.class)
                        .one()
        );
    }
}
