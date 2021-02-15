package shoeWebshop.model;

public class City {
    int id;
    String countyName;
    String cityName;
    int zipCode;

    public City(int id, String cityName, int zipCode) {
        this.id = id;
        this.cityName = cityName;
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", countyName='" + countyName + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
