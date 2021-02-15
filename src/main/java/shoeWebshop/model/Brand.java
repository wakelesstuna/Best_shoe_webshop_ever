package shoeWebshop.model;

public class Brand {
    int id;
    String brandName;

    public Brand (int id, String brandName){
        this.id = id;
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
