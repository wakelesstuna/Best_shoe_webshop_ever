package shoeWebshop.model;


import shoeWebshop.model.Utils.Database;

public class Brand {
    int id;
    String brandName;

    public Brand (int id, String brandName){

    }
    
    public Brand getBrand(int id, String c){
        return Database.brands.stream().filter(b -> b.equals(c)).map(b -> new Brand(id, c)).findFirst().orElse(null);

    }
}
