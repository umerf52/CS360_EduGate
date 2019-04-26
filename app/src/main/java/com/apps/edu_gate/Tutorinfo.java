package com.apps.edu_gate;

import java.io.Serializable;
//import java.util.HashMap;
import java.util.Map;

public class Tutorinfo implements Serializable {

    public String subject, grade, address, cnicNo, contactNo, emailAddress, firstName, gender, lastName, recentInstitution, tuitionLocation, urlImage;
    public int profileStatus;
//    public HashMap<String, String> grade;

    public Map<String, Double> rating;
    public double tempr = 0;
    public Tutorinfo() {
    }
}