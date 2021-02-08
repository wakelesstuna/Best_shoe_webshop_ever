package shoeWebshop.model;
import shoeWebshop.model.Utils.Database;

public class Brand {
    int id;
    String brandName;

    public Brand (int id, String brandName){
        this.id = id;
        this.brandName = brandName;
    }
    
    public static Brand getBrand(String c){
        return Database.brands.stream().filter(b -> b.equals(c)).map(b -> new Brand(b.id, c)).findFirst().orElse(null);

    }
}
