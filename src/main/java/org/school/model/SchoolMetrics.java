package org.school.model;

public class SchoolMetrics {
    private int id;
    private int schoolId;
    private int students;
    private double teachers;
    private double calworks;
    private double lunch;
    private int computer;
    private double expenditure;
    private double income;
    private double english;
    private double read;
    private double math;

    public SchoolMetrics() {
    }

    public SchoolMetrics(
            int students,
            double teachers,
            double calworks,
            double lunch,
            int computer,
            double expenditure,
            double income,
            double english,
            double read,
            double math
    ) {
        this.students = students;
        this.teachers = teachers;
        this.calworks = calworks;
        this.lunch = lunch;
        this.computer = computer;
        this.expenditure = expenditure;
        this.income = income;
        this.english = english;
        this.read = read;
        this.math = math;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public double getTeachers() {
        return teachers;
    }

    public void setTeachers(double teachers) {
        this.teachers = teachers;
    }

    public double getCalworks() {
        return calworks;
    }

    public void setCalworks(double calworks) {
        this.calworks = calworks;
    }

    public double getLunch() {
        return lunch;
    }

    public void setLunch(double lunch) {
        this.lunch = lunch;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }

    public double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(double expenditure) {
        this.expenditure = expenditure;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public double getRead() {
        return read;
    }

    public void setRead(double read) {
        this.read = read;
    }

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }
}
