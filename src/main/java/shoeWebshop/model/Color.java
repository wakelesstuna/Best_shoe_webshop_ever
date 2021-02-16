package shoeWebshop.model;

public class Color {
    int id;
    String color;

    public Color (int id, String color){
        this.id = id;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
