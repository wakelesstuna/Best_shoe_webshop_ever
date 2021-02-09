package shoeWebshop.model;


import shoeWebshop.model.Utils.Database;

public class Rate {
    int id;
    String rateText;
    int rateNumber;

    public Rate(int id, String ratingText, int ratingNumber) {
        this.id = id;
        this.rateText = ratingText;
        this.rateNumber = ratingNumber;
    }

    public static Rate getRatings(int id){
        return Database.getRates().stream().filter(b -> b.id== id).map(b -> new Rate(b.id, b.rateText, b.rateNumber)).findFirst().orElse(null);

    }
}
