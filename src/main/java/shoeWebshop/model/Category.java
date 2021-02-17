package shoeWebshop.model;

public class Category {
    int id;
    String CategoryName;

    public Category(int id, String categoryName) {
        this.id = id;
        this.CategoryName= categoryName;
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
