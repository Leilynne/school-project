package org.school.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.school.db.CountyRepository;
import org.school.db.DatabaseService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AvgStudentsByCountiesGraphBuilder {
    CountyRepository countyRepository;

    public AvgStudentsByCountiesGraphBuilder(DatabaseService dbService) {
        this.countyRepository = dbService.getCountyRepository();
    }

    public void buildGraph(String... counties) {
        Map<String, Double> averageStudentsByCounties = this.countyRepository.getAverageStudentsByCounties(counties);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        averageStudentsByCounties.forEach((county, avgStudents) ->
                dataset.addValue(avgStudents, "Студенты", county)
        );

        JFreeChart chart = ChartFactory.createBarChart(
                "Среднее число студентов по County",
                "County",
                "Студенты",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        try {
            ChartUtils.saveChartAsPNG(new File("graph.png"), chart, 1600, 900);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка построения графика");
        }
    }
}
