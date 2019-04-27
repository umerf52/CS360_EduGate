package com.apps.edu_gate;

import java.io.Serializable;
import java.util.ArrayList;

//import java.util.HashMap;

public class Tutorinfo implements Serializable {

    public String key, subject, grade, address, cnicNo, contactNo, emailAddress, firstName, gender, lastName, recentInstitution, tuitionLocation, profileImage;
    public int profileStatus;
//    public HashMap<String, String> grade;

    public ArrayList<Double> rating;
    public double tempr = 0;
    public Tutorinfo() {
    }

    public String getProfileImage() {
        return profileImage;
    }
}