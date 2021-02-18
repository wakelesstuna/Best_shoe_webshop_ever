package shoeWebshop.model;

import java.sql.Date;

public class ReviewObject {

    int id;
    String customerName;
    String productName;
    double size;
    double rating;
    String review;
    Date date;

    public ReviewObject(int id, String customerName, String productName, double size, double rating, String review, Date date) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.size = size;
        this.rating = rating;
        this.review = review;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReviewObject{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                ", size=" + size +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", date=" + date +
                '}';
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public double getSize() {
        return size;
    }

    public String getReview() {
        return review;
    }

    public double getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }
}
