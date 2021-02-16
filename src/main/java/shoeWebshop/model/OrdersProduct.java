package shoeWebshop.model;

public class OrdersProduct {
    int id;
    Orders orders;
    Product product;
    double productPrice;
    int quantity;

    public OrdersProduct(int id, Orders orders, Product product, double productPrice, int quantity) {
        this.id = id;
        this.orders = orders;
        this.product = product;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrdersProduct{" +
                "id=" + id +
                ", orders=" + orders +
                ", product=" + product +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                '}';
    }
}
