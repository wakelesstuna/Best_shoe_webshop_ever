package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Color {
    int id;
    String color;

    public Color (int id, String color){
        this.id = id;
        this.color = color;
    }

    public static Color getColor(int c){
        return Database.getColors().stream().filter(e -> e.id == c).map(b -> new Color(b.id, b.color)).findFirst().orElse(null);
    }
}
