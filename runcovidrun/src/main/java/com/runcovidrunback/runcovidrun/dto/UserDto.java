package com.runcovidrunback.runcovidrun.dto;

import java.util.Calendar;

public class UserDto {

    private String name;
    private String pass;
    private Float score;
    private String dateCreation;

    public UserDto() {
    }

    public UserDto(String name, String pass, Float score, String dateCreation) {
        this.name = name;
        this.pass = pass;
        this.score = score;
        this.dateCreation = dateCreation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}
