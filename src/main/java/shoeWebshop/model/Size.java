package shoeWebshop.model;

public class Size {

    private final int id;
    private final double eu;
    private final double uk;
    private final double us;
    private final double cm;

    public Size (int id, double eu, double us, double uk, double cm){
        this.id = id;
        this.eu = eu;
        this.us = us;
        this.uk = uk;
        this.cm = cm;

    }

    public double getEu() {
        return eu;
    }

    public double getUk() {
        return uk;
    }

    public double getUs() {
        return us;
    }

    public double getCm() {
        return cm;
    }

    @Override
    public String toString() {
        return "Size{" +
                "id=" + id +
                ", eu=" + eu +
                ", uk=" + uk +
                ", us=" + us +
                ", cm=" + cm +
                '}';
    }
}

