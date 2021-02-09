package shoeWebshop.model;


import shoeWebshop.model.Utils.Database;

public class Category {
    int id;
    String CategoryName;

    public Category(int id, String categoryName) {
        this.id = id;
        this.CategoryName= categoryName;
    }

    public static Category getCategory(int id){
        return Database.getCategorys().stream().filter(b -> b.id==id).map(b -> new Category(b.id, b.CategoryName)).findFirst().orElse(null);

    }

}
