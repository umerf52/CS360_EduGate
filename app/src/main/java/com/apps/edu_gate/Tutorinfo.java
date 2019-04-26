package com.apps.edu_gate;

import java.io.Serializable;
import java.util.HashMap;

public class Tutorinfo implements Serializable {

    public String address, cnicNo, contactNo, emailAddress, firstName, gender, lastName, recentInstitution, tutionLocation, urlImage;
    public int profileStatus;
    public HashMap<String, String> grade;
    public HashMap<String, String> subject;
    public HashMap<String, Double> rating;


    public Tutorinfo() {
    }
}