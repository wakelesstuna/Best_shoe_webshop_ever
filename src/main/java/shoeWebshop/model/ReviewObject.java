package shoeWebshop.model;

public class ReviewObject {

    int id;
    String productName;
    double size;
    double rating;
    String review;

    public ReviewObject(int id, String productName, double size, double rating, String review) {
        this.id = id;
        this.productName = productName;
        this.size = size;
        this.rating = rating;
        this.review = review;
    }

    @Override
    public String toString() {
        return "ReviewObject{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", size=" + size +
                ", averageRating=" + rating +
                ", review='" + review + '\'' +
                '}';
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
}
