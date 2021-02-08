package shoeWebshop.model;


import shoeWebshop.model.Utils.Database;

public class Brand {
    int id;
    String brandName;

    public Brand (int id, String brandName){

    }
    
    public Brand getBrand(int id, String c){
        for (brandName b : Database.brands) {
            if (b.equals(c))
                Brand brand = new Brand(id, c);
        }
        
        return brand;
    }
}
