package shoeWebshop.model;

public class Brand {
    private final int id;
    private final String brandName;

    public Brand (int id, String brandName){
        this.id = id;
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
