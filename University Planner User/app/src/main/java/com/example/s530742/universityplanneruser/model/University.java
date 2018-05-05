package com.example.s530742.universityplanneruser.model;

public class University {

    private String stateID;
    private String universityName;
    private String courseNames;

    public University()
    {

    }
    public University(String stateID, String universityName, String courseNames) {
        this.stateID = stateID;
        this.universityName = universityName;
        this.courseNames = courseNames;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(String courseNames) {
        this.courseNames = courseNames;
    }
}
