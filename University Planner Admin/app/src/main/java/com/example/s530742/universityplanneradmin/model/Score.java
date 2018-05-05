package com.example.s530742.universityplanneradmin.model;

public class Score {

    String universtiyID;
    String courseID;
    int gre,tofel;
    double ielts;
    public Score(){

    }

    public Score(String universtiyID, String courseID, int gre, int tofel, double ielts) {
        this.universtiyID = universtiyID;
        this.courseID = courseID;
        this.gre = gre;
        this.tofel = tofel;
        this.ielts = ielts;
    }

    public String getUniverstiyID() {
        return universtiyID;
    }

    public void setUniverstiyID(String universtiyID) {
        this.universtiyID = universtiyID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getGre() {
        return gre;
    }

    public void setGre(int gre) {
        this.gre = gre;
    }

    public int getTofel() {
        return tofel;
    }

    public void setTofel(int tofel) {
        this.tofel = tofel;
    }

    public double getIelts() {
        return ielts;
    }

    public void setIelts(double ielts) {
        this.ielts = ielts;
    }
}
