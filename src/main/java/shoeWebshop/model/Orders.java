package shoeWebshop.model;

import java.sql.Date;

public class Orders {
    private final int id;
    private Date date;
    private final Customer customer;
    private double totalPrice;
    private int numberOfProducts;

    public Orders(int id, Date date, Customer customer, double totalPrice, int numberOfProducts) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.numberOfProducts = numberOfProducts;
    }

    public Orders(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer +
                ", totalPrice=" + totalPrice +
                ", numberOfProducts=" + numberOfProducts +
                '}';
    }
}
