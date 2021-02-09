package shoeWebshop.model.Utils;

import shoeWebshop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;


public class Database extends Credentials {

    static final String DB_NAME = "sql_shoe_webshop";
    static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    static final String USER_NAME = "root";
    static final String PASSWORD = "Jedi";

    private static Connection connection;

    public static void main(String[] args) {
        Database pro = new Database();
        pro.createConnection();
        pro.getAllCustomers();
        pro.getAllProducts();
        //pro.writeAllInfo(customers);
        //pro.writeAllInfo(products);
    }

    public static void createConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING + DB_NAME, USER_NAME, PASSWORD);
            System.out.println("DataBase connection Success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> getAllCustomers() {
        createConnection();
        List<Customer> customers = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer JOIN city ON customer.fk_city_id = city.id");
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phoneNumber = rs.getInt("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String socialSecurityNumber = rs.getString("social_security_number");
                String address = rs.getString("address");
                String tempCity = rs.getString("city_name");
                City city = City.getCity(tempCity);

                customers.add(new Customer(id, firstName, lastName, phoneNumber, email, password, socialSecurityNumber, address, city));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public static List<Size> getSizes() {
        createConnection();
        List<Size> sizes = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM size ");

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                double eu = Double.parseDouble(rs.getString("eu"));
                double us = Double.parseDouble(rs.getString("us"));
                double uk = Double.parseDouble(rs.getString("uk"));
                double cm = Double.parseDouble(rs.getString("cm"));
                sizes.add(new Size(id, eu, us, uk, cm));

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return sizes;
    }

    public static List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM brand ");

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String brand = (rs.getString("brand_name"));

                brands.add(new Brand(id, brand));
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return brands;
    }

    public List<City> getCitys() {
        createConnection();
        List<City> citys = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM city ");

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String tempCity = (rs.getString("city_name"));

                citys.add(new City(id, tempCity));
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return citys;
    }

    public List<Category> getCategorys() {
        List<Category> category = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM category ");

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String categoryName = (rs.getString("category_name"));

                category.add(new Category(id, categoryName));
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return category;
    }

    public static List<Color> getColors() {
        List<Color> colors = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM color ");

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String color = (rs.getString("color"));

                colors.add(new Color(id, color));
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return colors;
    }
    public static List<Orders> getOrders() {
        List<Orders> orders = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders JOIN customer ON orders.fk_customer_id = costumer.id");

            while (rs.next()) {
                int id = (rs.getInt("id"));
                //String color = (rs.getString("date"));
                // todo: lös hur man läser in datumet rätt

                int cId = (rs.getInt("fk_customer_id"));
                Customer customer = Customer.getCustomer(cId);


                orders.add(new Orders(id, customer));
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return orders;
    }

    public  List<Product> getAllProducts() {
        createConnection();
        List<Product> products = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product " +
                    "JOIN color ON product.fk_color_id = color.id " +
                    "JOIN size ON product.fk_size_id = size.id " +
                    "JOIN brand ON product.fk_brand_id = brand.id");

            while (rs.next()) {
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                String tempColor = rs.getString("color");
                Color color = Color.getColor(tempColor);

                double tempSize = rs.getInt("eu");
                Size size = Size.getSize(tempSize);

                String tempBrand = rs.getString("brand_name");
                Brand brand = Brand.getBrand(tempBrand);

                int stock = Integer.parseInt(rs.getString("stock"));

                products.add(new Product(productName, priceSek, color, size, brand, stock));
            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return products;
    }

    public void writeAllInfo(List<?> list) {
        int count = 1;
        for (Object e : list) {
            System.out.println(count + ": " + e);
            count++;
        }
    }


}