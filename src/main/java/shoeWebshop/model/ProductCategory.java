package shoeWebshop.model;


public class ProductCategory {
    int id;
    Product product;
    Category category;

    public ProductCategory(int id, Category category, Product product) {
        this.id = id;
        this.product = product;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", product=" + product +
                ", category=" + category +
                '}';
    }
}
