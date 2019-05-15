package com.apps.edu_gate;

import java.io.Serializable;
import java.util.ArrayList;

//import java.util.HashMap;

public class TutorInfo implements Serializable {

    public String key, subject, grade, address, cnicNo, contactNo, emailAddress, firstName, gender, lastName, recentInstitution, tuitionLocation, profileImage, transcriptImage, degree;
    public int profileStatus;

    public ArrayList<Double> rating;
    public double tempr = 0;

    public TutorInfo() {
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getTranscriptImage() {
        return transcriptImage;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getTuitionLocation() {
        return tuitionLocation;
    }

    public String getDegree() {
        return degree;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String newKey) {
        key = newKey;
    }
}