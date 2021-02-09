package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Color {
    int id;
    String color;

    public Color (int id, String color){
        this.id = id;
        this.color = color;
    }

    public static Color getColor(String c){
        return Database.getColors().stream().filter(b -> b.equals(c)).map(b -> new Color(b.id, c)).findFirst().orElse(null);
    }
}
