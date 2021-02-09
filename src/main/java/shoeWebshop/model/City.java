package shoeWebshop.model;

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
}
