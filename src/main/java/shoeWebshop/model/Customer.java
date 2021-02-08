package shoeWebshop.model;

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
    public Customer(String firstName, String lastName, int phoneNumber, String email, String password, String socialSecurityNumber, String address, City city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.ssn = socialSecurityNumber;
        this.Address = address;
        this.city = city;
    }

    public String getFullName() {
        return firstName + " " + lastName;
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


}}
