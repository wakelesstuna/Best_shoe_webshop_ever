package shoeWebshop.model;

import shoeWebshop.model.Utils.Database;

public class Size {
    int id;
    double eu;
    double uk;
    double us;
    double cm;

    public Size (int id, double eu, double us, double uk, double cm){
        this.id = id;
        this.eu = eu;
        this.us = us;
        this.uk = uk;
        this.cm = cm;

    }
    public Size (int id, double eu){
        this.id = id;
        this.eu = eu;
    }

    public static Size getSize(int id){
        return Database.getSizes().stream().filter(b -> b.id==id).map(b -> new Size(b.id, b.eu)).findFirst().orElse(null);
    }
}
