package com.helidevine.learningspringboot.model;

import java.util.UUID;

public class User {
    private String firstName;
    private String lastName;
    private Gender gender;
    private int age;
    private String email;
    // user id
    private UUID userUid;

    public User(UUID userUid, String firstName, String lastName, Gender gender, int age, String email) {
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public User() {
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid=" + userUid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public enum Gender {
        FEMALE,
        MALE,
        PREFER_NOT_TO_SAY
    }

    // adding just a comment to check git
}
