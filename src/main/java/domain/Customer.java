/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author winbr386
 */
public class Customer {
    private Integer personID;
    private String username;
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String address;
    private String creditCardDetails;
    
    public Customer(){
        
    }

    public Customer(Integer personID, String username, String firstName, String surname, String password, String email, String address, String creditCardDetails) {
        this.personID = personID;
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.creditCardDetails = creditCardDetails;
    }

    public Integer getPersonID() {
        return personID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    @Override
    public String toString() {
        return "Customer{" + "personID=" + personID + ", username=" + username + ", firstName=" + firstName + ", surname=" + surname + ", password=" + password + ", email=" + email + ", address=" + address + ", creditCardDetails=" + creditCardDetails + '}';
    }

    
}
