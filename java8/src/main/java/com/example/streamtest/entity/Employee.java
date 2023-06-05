package com.example.streamtest.entity;//看包名就知道这个是hibernate附加的constraints

import com.sun.istack.internal.NotNull;

import java.util.Date;

/**
 * @author Kern
 * @Title: Employee
 * @ProjectName kern-demo
 * @Description: TODO
 * @date 2019/9/1718:53
 */
public class Employee {


    private String name;


    private String badgeCode;


    private String gender;


    private Date birthDate;
    private int age;

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", badgeCode='" + badgeCode + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Employee() {
    }

    public Employee(String name, String badgeCode, String gender, Date birthDate, int age) {
        this.name = name;
        this.badgeCode = badgeCode;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadgeCode() {
        return badgeCode;
    }

    public void setBadgeCode(String badgeCode) {
        this.badgeCode = badgeCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
