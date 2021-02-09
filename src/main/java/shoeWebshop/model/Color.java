package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Color {
    int id;
    String color;

    public Color (int id, String color){
        this.id = id;
        this.color = color;
    }

    public static Color getColor(int id){
        return Database.getColors().stream().filter(b -> b.id==id).map(b -> new Color(b.id, b.color)).findFirst().orElse(null);
    }
}
