package org.school.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DatabaseService {
    private final CountyRepository countyRepository;
    private final GradesRangeRepository gradesRangeRepository;
    private final SchoolRepository schoolRepository;

    public DatabaseService() {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:schools.db");
        jdbi.installPlugin(new SqlObjectPlugin());

        this.countyRepository = new CountyRepository(jdbi);
        this.gradesRangeRepository = new GradesRangeRepository(jdbi);
        this.schoolRepository = new SchoolRepository(jdbi);

        createTables();
    }

    private void createTables() {
        countyRepository.createTable();
        gradesRangeRepository.createTable();
        schoolRepository.createTable();
    }

    public CountyRepository getCountyRepository() {
        return countyRepository;
    }

    public GradesRangeRepository getGradesRangeRepository() {
        return gradesRangeRepository;
    }

    public SchoolRepository getSchoolRepository() {
        return schoolRepository;
    }
}