package shoeWebshop.model;

public class City {
    private int id;
    private String cityName;
    private int zipCode;

    public City() {
    }

    public City(int id, String cityName, int zipCode) {
        this.id = id;
        this.cityName = cityName;
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public int getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", countyName='" + cityName + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
