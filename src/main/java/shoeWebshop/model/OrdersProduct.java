package shoeWebshop.model;

/**
 * Created by Patrik Melander
 * Date: 2021-02-04
 * Time: 09:23
 * Project: Best_shoe_webshop_ever
 * Copyright: MIT
 */
public class OrdersProduct {
    int id;
    Orders ordersId;
    Product productId;
    double productPrice; // denna ska ta värdet ifrån product klassen när den skapas
    int quantity;
}
