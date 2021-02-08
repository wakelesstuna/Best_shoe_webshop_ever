package shoeWebshop.model;

public class Product {

    String productName;
    double priceSek;
    int stock;
    String color;
    int size;
    String brand;

    public Product(String productName, double priceSek, int stock, String color, int size, String brand) {

        this.productName = productName;
        this.priceSek = priceSek;
        this.stock = stock;
        this.color = color;
        this.size = size;
        this.brand = brand;
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
        return color;
    }

    public int getSize() {
        return size;
    }

    public String getBrand() {
        return brand;
    }

}
