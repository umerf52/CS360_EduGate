package com.apps.edu_gate;

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


    public Tutor(String firstname, String lastname, String cnicNo, String address, String contactNo,
                 String gender, String recentInstitution, String tuitionLocation){
        this.mFirstName = firstname;
        this.mLastName = lastname;
        this.mCnicNo = cnicNo;
        this.mAddress = address;
        this.mCnicNo = contactNo;
        this.mGender = gender;
        this.mRecentInstitution = recentInstitution;
        this.mTuitionLocation = tuitionLocation;
        this.mProfileStatus = -1;
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

    public void setKey(String key) {
        this.key = key;
    }
}
