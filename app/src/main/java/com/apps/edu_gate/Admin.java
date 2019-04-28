package com.apps.edu_gate;

public class Admin {
    private String mFirstName;
    private String mLastName;
    private String mEmailAddress;
    private String mContactNo;

    public Admin(){

    }

    public Admin(String first_name, String last_name, String email, String contact_no) {
        this.mFirstName = first_name.toLowerCase();
        this.mLastName = last_name.toLowerCase();
        this.mContactNo = contact_no;
        this.mEmailAddress = email;
        }

    public String getFirstName() {
        return mFirstName;
    }
    public String getLastName() {
        return mLastName;
    }
    public String getmEmailAddress() {
        return mEmailAddress;
    }
    public String getmContactNo() {
        return mContactNo;
    }



}
