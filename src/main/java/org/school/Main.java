package org.school;

import org.school.db.DatabaseService;
import org.school.model.School;
import org.school.service.AvgStudentsByCountiesGraphBuilder;
import org.school.service.CsvParser;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DatabaseService databaseService = new DatabaseService();
        parseFile(databaseService);

        firstTask(databaseService);
        secondTask(databaseService);
        thirdTask(databaseService);
    }

    private static void thirdTask(DatabaseService databaseService) {
        System.out.println("Лучшие школы по результатам математики:");
        School firstSchool = databaseService.getSchoolRepository().getTopSchoolByMath(5000, 7500);
        System.out.println("От 5000 до 7500 студентов: " + firstSchool.getName() + ", " + firstSchool.getDistrict());
        School secondSchool = databaseService.getSchoolRepository().getTopSchoolByMath(10000, 11000);
        System.out.println("От 10000 до 11000 студентов: " + secondSchool.getName() + ", " + secondSchool.getDistrict());
    }

    private static void secondTask(DatabaseService databaseService) {
        Map<String, Double> averageExpenditure = databaseService.getSchoolRepository()
                .findAverageExpenditureByCounties("Fresno", "Contra Costa", "El Dorado", "Glenn");

        System.out.println("Среднее значение расходов школ в Fresno, Contra Costa, El Dorado, Glenn:");
        averageExpenditure.forEach((county, value) -> {
            System.out.println(county + " - " + value);
        });
    }

    private static void firstTask(DatabaseService databaseService) {
        AvgStudentsByCountiesGraphBuilder graphBuilder = new AvgStudentsByCountiesGraphBuilder(databaseService);
        graphBuilder.buildGraph("Alameda", "Butte", "Fresno", "San Joaquin", "Kern", "Sacramento", "Merced", "Tulare", "Los Angeles", "Imperial");
    }

    private static void parseFile(DatabaseService databaseService) {
        CsvParser parser = new CsvParser(databaseService);
        parser.parseAndSave("schools.csv");
    }
}