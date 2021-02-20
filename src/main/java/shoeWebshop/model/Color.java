package shoeWebshop.model;

public class Color {
    private final int id;
    private final String color;

    public Color (int id, String color){
        this.id = id;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
