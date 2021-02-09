package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Product {

    int id;
    String productName;
    double priceSek;
    Color color;
    Size size;
    Brand brand;
    int stock;

    public Product(int id, String productName, double priceSek, Color color, Size size, Brand brand, int stock) {
        this.id = id;
        this.productName = productName;
        this.priceSek = priceSek;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.stock = stock;

    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", priceSek=" + priceSek +
                ", color='" + color + '\'' +
                ", sizeEu=" + size.eu +
                ", sizeUk=" + size.uk +
                ", sizeUs=" + size.us +
                ", sizeCm=" + size.cm +
                ", brand='" + brand + '\'' +
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


    public static Product getProduct(int c){
        return Database.getAllProducts().stream().filter(b -> b.equals(c)).map(b -> new Product(b.id,b.productName, b.priceSek, b.color, b.size, b.brand, b.stock)).findFirst().orElse(null);

    }
}
