package shoeWebshop.model;


public class Rate {
    int id;
    String rateText;
    int rateNumber;

    public Rate(int id, String ratingText, int ratingNumber) {
        this.id = id;
        this.rateText = ratingText;
        this.rateNumber = ratingNumber;
    }
}
