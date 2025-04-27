package org.school.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.school.db.CountyRepository;
import org.school.db.GradesRangeRepository;
import org.school.db.SchoolRepository;
import org.school.model.County;
import org.school.model.GradesRange;
import org.school.db.DatabaseService;
import org.school.model.School;
import org.school.model.SchoolMetrics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CsvParser {
    CountyRepository countyRepository;
    GradesRangeRepository gradesRangeRepository;
    SchoolRepository schoolRepository;
    private final Map<String, Integer> countyCache = new HashMap<>();
    private final Map<String, Integer> gradesRangeCache = new HashMap<>();

    public CsvParser(DatabaseService dbService) {
        this.countyRepository = dbService.getCountyRepository();
        this.gradesRangeRepository = dbService.getGradesRangeRepository();
        this.schoolRepository = dbService.getSchoolRepository();
    }

    public void parseAndSave(String csvPath) {
        try (FileInputStream fileInputStream = new FileInputStream(csvPath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(inputStreamReader)) {

            String[] line;
            reader.readNext(); // skip table head
            while ((line = reader.readNext()) != null) {
                int countyId = handleCounty(line[3]);
                int gradesRangeId = handleGradesRange(line[4]);
                School school = new School(line[2], countyId, parseIntSafe(line[1]), gradesRangeId);
                SchoolMetrics metrics = new SchoolMetrics(
                        parseIntSafe(line[5]),
                        parseDoubleSafe(line[6]),
                        parseDoubleSafe(line[7]),
                        parseDoubleSafe(line[8]),
                        parseIntSafe(line[9]),
                        parseDoubleSafe(line[10]),
                        parseDoubleSafe(line[11]),
                        parseDoubleSafe(line[12]),
                        parseDoubleSafe(line[13]),
                        parseDoubleSafe(line[14])
                );
                schoolRepository.createSchoolWithMetrics(school, metrics);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Ошибка при чтении файла", e);
        }
    }

    private int handleGradesRange(String grades) {
        return gradesRangeCache.computeIfAbsent(grades, range -> {
            GradesRange gradesRange = new GradesRange(range);

            return gradesRangeRepository.create(gradesRange);
        });
    }

    private int handleCounty(String countyName) {
        return countyCache.computeIfAbsent(countyName, name -> {
            County county = new County(name);

            return countyRepository.create(county);
        });
    }

    private int parseIntSafe(String str) {
        try {
            return str == null || str.isBlank() ? 0 : Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDoubleSafe(String str) {
        try {
            return str == null || str.isBlank() ? 0 : Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
