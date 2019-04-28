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
    ArrayList<Double> mRating = new ArrayList<Double>();
    private String mGrade;
    private String mSubject;
    private int timesRated;
    private String mProfileImage;
    private String mTranscriptImage;
    private String mDegree;

    public Tutor() {
    }


    public Tutor(String first_name, String last_name, String email, String cnic_no, String address, String contact_no,
                 String gender, String recent_institution, String tuition_location, String grade,
                 String subject, String degree) {
        this.mFirstName = first_name.toLowerCase();
        this.mLastName = last_name.toLowerCase();
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
        this.mRating.add(5.0);
        this.timesRated = 0;
        this.mDegree = degree;
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

    public String getGrade() {
        return mGrade;
    }

    public String getSubject() {
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

    public String getContactNo() {
        return mContactNo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public ArrayList<Double> getRating() {
        return mRating;
    }

    public float getTimesRated() {
        return timesRated;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String file) {
        mProfileImage = file;
    }

    public String getTranscriptImage() {
        return mTranscriptImage;
    }

    public void setTranscriptImage(String file) {
        mTranscriptImage = file;
    }

    public String getDegree() {
        return mDegree;
    }

    public void setContactNo(String newNum) {
        mContactNo = newNum;
    }

    public void setDegree(String newDeg) {
        mDegree = newDeg;
    }

    public void setTuitionLocation(String newLoc) {
        mTuitionLocation = newLoc;
    }

    public void setSubject(String newSub) {
        mSubject = newSub;
    }

    public void setGrade(String newGrade) {
        mGrade = newGrade;
    }

}
