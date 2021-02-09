package shoeWebshop.model;

import java.time.LocalDate;

public class Orders {
    int id;
    LocalDate date;
    //tiden saknas här
    Customer customer;

    public Orders(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }
}
