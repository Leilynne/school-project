package org.school.model;

public class School {
    private int id;
    private String name;
    private int countyId;
    private int district;
    private int gradesRangeId;

    public School() {
    }

    public School(String name, int countyId, int district, int gradesRangeId) {
        this.name = name;
        this.countyId = countyId;
        this.district = district;
        this.gradesRangeId = gradesRangeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getGradesRangeId() {
        return gradesRangeId;
    }

    public void setGradesRangeId(int gradesRangeId) {
        this.gradesRangeId = gradesRangeId;
    }
}
