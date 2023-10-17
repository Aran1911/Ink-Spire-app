package com.inkSpire.application.dto.user;

import com.inkSpire.application.entity.Gender;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.util.Date;

public class UserRegistrationResponse {

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @Enumerated
    private Gender gender;
    @Past(message = "Date of birth must be in the past or present.")
    @NotNull(message = "Date of birth is required.")
    private Date dateOfBirth;

    @NotBlank(message = "Firstname is required.")
    private String firstname;

    @NotBlank(message = "Lastname is required.")
    private String lastname;

    @NotBlank(message = "Token is required.")
    private String jwtToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public UserRegistrationResponse() {
    }

    public UserRegistrationResponse(String email,
                                    String password,
                                    Gender gender,
                                    Date dateOfBirth,
                                    String firstname,
                                    String lastname,
                                    String jwtToken) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" + "\n" +
                "email='" + email + '\'' + "\n" +
                ", password='" + password + '\'' + "\n" +
                ", gender=" + gender + "\n" +
                ", dateOfBirth=" + dateOfBirth + "\n" +
                ", firstname='" + firstname + '\'' + "\n" +
                ", lastname='" + lastname + '\'' + "\n" +
                ", jwtToken='" + jwtToken + '\'' + "\n" +
                '}';
    }
}
