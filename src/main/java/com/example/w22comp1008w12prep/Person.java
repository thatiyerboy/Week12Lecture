package com.example.w22comp1008w12prep;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String firstName, lastName, address;
    private LocalDate birthday;

    public Person(String firstName, String lastName, String address, LocalDate birthday) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setBirthday(birthday);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName.trim();
        if (firstName.length()>=2 && firstName.length()<=20)
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("first name must be 2-20 characters");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName.trim();
        if (lastName.length()>=2 && lastName.length()<=30)
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("last name must be 2-30 characters");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address.trim();
        if (address.length()>=5 && address.length()<=100)
            this.address = address;
        else
            throw new IllegalArgumentException("address must be 5 to 100 characters in length");
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday == null)
            throw new IllegalArgumentException("a date is required for the birthday");
        if (birthday.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("birthday cannot be in the future");
        this.birthday = birthday;
    }

    public Integer getAge()
    {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public String toString()
    {
        return String.format("%s %s age: %d years old", firstName, lastName, getAge());
    }
}
