package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Customer {
    int id;
    String firstName;
    String lastName;
    int phoneNumber;
    String email;
    String password;
    String ssn;
    String Address;
    City city;

    public Customer(int id, String firstName, String lastName, int phoneNumber, String email, String password, String socialSecurityNumber, String address, City city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.ssn = socialSecurityNumber;
        this.Address = address;
        this.city = city;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", socialSecurityNumber='" + ssn + '\'' +
                ", Address='" + Address + '\'' +
                ", city='" + city + '\'';


    }
    public static Customer getCustomer(int c){
        return Database.getAllCustomers().stream().filter(b -> b.equals(c)).map(b -> new Customer(b.id, b.firstName, b.lastName, b.phoneNumber, b.email, b.password, b.ssn, b.Address, b.city)).findFirst().orElse(null);

    }
}
