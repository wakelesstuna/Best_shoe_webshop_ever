package shoeWebshop.model;

public class Customer {
    private final int id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;
    private String ssn;
    private String Address;
    private City city;

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

    public int getId() {
        return id;
    }

    public String getEmail() {return email;}

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ssn='" + ssn + '\'' +
                ", Address='" + Address + '\'' +
                ", city=" + city +
                '}';
    }

}
