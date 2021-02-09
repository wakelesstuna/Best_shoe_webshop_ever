package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class City {
    int id;
    String countyName;
    int zipCode;

    public City() {
    }

    public City(int id, String countyName, int zipCode) {
        this.id = id;
        this.countyName = countyName;
        this.zipCode = zipCode;
    }

    public String getCountyName() {
        return countyName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", countyName='" + countyName + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
    String cityName;

    public City (int id, String cityName){
        this.id = id;
        this.cityName = cityName;
    }

    public static City getCity(int id){
        return Database.getCitys().stream().filter(b -> b.id==id).map(b -> new City(b.id, b.cityName)).findFirst().orElse(null);

    }
}
