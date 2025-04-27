package org.school.model;

public class GradesRange {
    private int id;
    private String range;

    public GradesRange() {
    }

    public GradesRange(String range) {
        this.range = range;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
