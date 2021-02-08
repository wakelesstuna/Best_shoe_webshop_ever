package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Color {
    int id;
    String color;

    public Color (int id, String brandName){

    }

    public static Color getColor(String c){
        return Database.color.stream().filter(b -> b.equals(c)).map(b -> new Color(b.id, c)).findFirst().orElse(null);

    }
}
}
