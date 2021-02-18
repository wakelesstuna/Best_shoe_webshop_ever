package shoeWebshop.model;

public class Product {

    int id;
    String productName;
    double priceSek;
    Color color;
    Size size;
    Brand brand;
    int stock;
    int amountOrdered;
    double averageScore;

    public Product(double averageScore) {
        this.averageScore = averageScore;
    }

    public Product(int id, String productName, double priceSek, Color color, Size size, Brand brand, int stock) {
        this.id = id;
        this.productName = productName;
        this.priceSek = priceSek;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.stock = stock;
    }

    public Product(int id, String productName, double priceSek, Color color, Size size, int amountOrdered ,Brand brand) {
        this.id = id;
        this.productName = productName;
        this.priceSek = priceSek;
        this.color = color;
        this.size = size;
        this.amountOrdered = amountOrdered;
        this.brand = brand;

    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", priceSek=" + priceSek +
                ", color=" + color +
                ", size=" + size +
                ", brand=" + brand +
                ", stock=" + stock +
                ", amountOrdered=" + amountOrdered +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getPriceSek() {
        return priceSek;
    }

    public int getStock() {
        return stock;
    }

    public String getColor() {
        return color.color;
    }

    public double getSize() {
        return size.eu;
    }

    public String getBrand() {
        return brand.brandName;
    }

    public int getAmountOrdered() {
        return amountOrdered;
    }

}
