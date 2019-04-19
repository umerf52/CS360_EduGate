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
    private String mGrade;
    private String mSubject;

    public Tutor() {
    }


    public Tutor(String first_name, String last_name, String email, String cnic_no, String address, String contact_no,
                 String gender, String recent_institution, String tuition_location, String grade, String subject) {
        this.mFirstName = first_name;
        this.mLastName = last_name;
        this.mCnicNo = cnic_no;
        this.mAddress = address;
        this.mContactNo = contact_no;
        this.mGender = gender;
        this.mRecentInstitution = recent_institution;
        this.mTuitionLocation = tuition_location;
        this.mProfileStatus = -1;
        this.mGrade = grade;
        this.mSubject = subject;
        this.mEmailAddress = email;
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

    public String getKey() {
        return key;
    }

    public String getContactNo() {
        return mContactNo;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
