package com.apps.edu_gate;

import java.util.ArrayList;

public class Tutor {
    private String mFirstName;
    private String mLastName;
    private String mCnicNo;
    private String mAddress;
    private String mContactNo;
    private String mGender;
    private String mRecentInstitution;
    private String mTuitionLocation;
    private String mEmailAddress;
    private int mProfileStatus;
    private String key;
    private ArrayList<String> mGrade = new ArrayList<String>();
    private ArrayList<String> mSubject = new ArrayList<String>();
    private double mRating;
    private int timesRated;

    public Tutor() {
    }


    public Tutor(String first_name, String last_name, String email, String cnic_no, String address, String contact_no,
                 String gender, String recent_institution, String tuition_location, ArrayList<String> grade, ArrayList<String> subject) {
        this.mFirstName = first_name;
        this.mLastName = last_name;
        this.mCnicNo = cnic_no;
        this.mAddress = address;
        this.mContactNo = contact_no;
        this.mGender = gender;
        this.mRecentInstitution = recent_institution;
        this.mTuitionLocation = tuition_location;
        this.mProfileStatus = -1;
        this.mEmailAddress = email;
        this.mGrade = grade;
        this.mSubject = subject;
        this.mRating = 5.0;
        this.timesRated = 0;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getGender() {
        return mGender;
    }

    public String getRecentInstitution() {
        return mRecentInstitution;
    }

    public ArrayList<String> getGrade() {
        return mGrade;
    }

    public ArrayList<String> getSubject() {
        return mSubject;
    }

    public String getCnicNo() {
        return mCnicNo;
    }

    public String getTuitionLocation() {
        return mTuitionLocation;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public int getProfileStatus() {
        return mProfileStatus;
    }

    public String getKey() {
        return key;
    }

    public String getContactNo() {
        return mContactNo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getRating() {
        return mRating;
    }

    public float getTimesRated() {
        return timesRated;
    }
}
