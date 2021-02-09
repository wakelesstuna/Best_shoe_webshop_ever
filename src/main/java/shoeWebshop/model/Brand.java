package shoeWebshop.model;
import shoeWebshop.model.Utils.Database;

public class Brand {
    int id;
    String brandName;

    public Brand (int id, String brandName){
        this.id = id;
        this.brandName = brandName;
    }
    
    public static Brand getBrand(int c){
        return Database.getBrands().stream().filter(b -> b.id==c).map(b -> new Brand(b.id, b.brandName)).findFirst().orElse(null);

    }
}
