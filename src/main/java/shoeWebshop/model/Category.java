package shoeWebshop.model;

import java.util.List;

public class Category {
    int id;
    String CategoryName;
    List<Product> products;

    public Category(int id, String categoryName) {
        this.id = id;
        this.CategoryName= categoryName;
    }

    public Category(int id, String categoryName, List<Product> products) {
        this.id = id;
        CategoryName = categoryName;
        this.products = products;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", CategoryName='" + CategoryName + '\'' +
                '}';
    }
}
