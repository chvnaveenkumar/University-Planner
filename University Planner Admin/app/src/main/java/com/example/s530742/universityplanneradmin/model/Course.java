package com.example.s530742.universityplanneradmin.model;

public class Course {

    String courseID;
    String courseName;

    public Course(String courseName) {

        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
