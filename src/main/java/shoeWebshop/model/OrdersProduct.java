package shoeWebshop.model;

public class OrdersProduct {
    int id;
    Orders orders;
    Product product;
    double productPrice; // denna ska ta värdet ifrån product klassen när den skapas
    int quantity;

    public OrdersProduct(int id, Orders orders, Product product, double productPrice, int quantity) {
        this.id = id;
        this.orders = orders;
        this.product = product;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
}
