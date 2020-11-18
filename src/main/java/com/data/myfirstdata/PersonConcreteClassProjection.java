package com.data.myfirstdata;

import lombok.Getter;

@Getter
public class PersonConcreteClassProjection {
    private String firstName;
    private String address;

    public PersonConcreteClassProjection(String firstName, String address) {
        this.firstName = firstName;
        this.address = address;
    }


    //    public String getFirstNameWithAddress() {
//        return firstName + " " + address;
//    }
}
