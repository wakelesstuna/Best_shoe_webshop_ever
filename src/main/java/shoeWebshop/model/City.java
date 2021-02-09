package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class City {
    int id;
    String cityName;

    public City (int id, String cityName){
        this.id = id;
        this.cityName = cityName;
    }

    public static City getCity(String c){
        return Database.getCitys().stream().filter(b -> b.equals(c)).map(b -> new City(b.id, c)).findFirst().orElse(null);

    }
}
