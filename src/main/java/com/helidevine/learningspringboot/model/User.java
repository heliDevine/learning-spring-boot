package com.helidevine.learningspringboot.model;

import java.util.UUID;

public class User {
    // user id
    private final UUID userUid;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final int age;
    private final String email;

    public User(UUID userUid, String firstName, String lastName, Gender gender, int age, String email) {
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public UUID getUserUid() {
        return userUid;
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

    public enum Gender{
        FEMALE,
        MALE,
        PREFER_NOT_TO_SAY
    }

    // adding just a comment to check git
}