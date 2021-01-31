package com.example.sqlite;

import androidx.annotation.NonNull;

public class CustomerModel {

    // let us create an id here in this case
    private int id;
    // so we will have a String for the name, int for the age, boolean for the isActive
    private String name;
    private int age;
    private Boolean isActive;

    // now we will create the constructor
    public CustomerModel(int id, String name, int age, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    // and now for our getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    // now also remember that if you want to be able to print you need to string

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}';
    }
}
