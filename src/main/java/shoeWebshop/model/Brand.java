package shoeWebshop.model;


import shoeWebshop.model.Utils.Database;

public class Brand {
    int id;
    String brandName;

    public Brand (int id, String brandName){

    }
    
    public Brand getBrand(int id, String b){
        for (brandName b : Database.brands) {
            if (brandName.equals(b)
                Brand brand = new Brand(id, b);
        }
        
        return brand;
    }
}
