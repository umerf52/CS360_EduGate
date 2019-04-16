package com.apps.edu_gate;

public class Tutor {
    private String Firstname;
    private String Lastname;
    private String CnicNo;
    private String Address;
    private String ContactNo;
    private String Gender;
    private String RecentInstitution;
    private String TuitionLocation;
    private String EmailAddress;
    boolean ProfileStatus;


    public Tutor(String firstname, String lastname, String cnicNo, String address, String contactNo,
                 String gender, String recentInstitution, String tuitionLocation){
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.CnicNo = cnicNo;
        this.Address = address;
        this.ContactNo = contactNo;
        this.Gender = gender;
        this.RecentInstitution = recentInstitution;
        this.TuitionLocation = tuitionLocation;
        this.ProfileStatus = false;
    }

    public String getCnicNo() {
        return CnicNo;
    }

    public String getTuitionLocation() {
        return TuitionLocation;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public boolean isProfileStatus() {
        return ProfileStatus;
    }
}
