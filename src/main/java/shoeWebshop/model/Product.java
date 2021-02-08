package shoeWebshop.model;

public class Product {
    String productName;
    double priceSek;
    String color;
    Size size;
    Brand brand;
    int stock;

    public Product(String productName, double priceSek, Color color, Size size, Brand brand, int stock) {
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

    public double getSize() {

        return size.eu;
    }

    public String getBrand() {
        return brand.brandName;
    }

}
