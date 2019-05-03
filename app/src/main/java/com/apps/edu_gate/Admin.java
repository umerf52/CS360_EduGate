package com.apps.edu_gate;

public class Admin {
    private String FirstName;
    private String LastName;
    private String EmailAddress;
    private String ContactNo;

    public Admin(String first_name, String last_name, String email, String contact_no) {
        this.FirstName = first_name.toLowerCase();
        this.LastName = last_name.toLowerCase();
        this.ContactNo = contact_no;
        this.EmailAddress = email;
        }

    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getContactNo() {
        return ContactNo;
    }

}
