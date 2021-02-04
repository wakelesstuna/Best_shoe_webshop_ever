package shoeWebshop.model;

public class OrdersProduct {
    int id;
    Orders ordersId;
    Product productId;
    double productPrice; // denna ska ta värdet ifrån product klassen när den skapas
    int quantity;
}
