package shoeWebshop.model.Utils;

import shoeWebshop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class Database extends Credentials {

    static final String DB_NAME = "sql_shoe_webshop";
    static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    static final String USER_NAME = "root";
    static final String PASSWORD = "Jedi";

    private static Connection connection;

    private static List<Customer> customers = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static List<Brand> brand = new ArrayList<>();
    private static List<Category> category = new ArrayList<>();
    private static List<Color> color = new ArrayList<>();

    public static void main(String[] args) {
        Database pro = new Database();
        pro.createConnection();
        pro.getAllCustomers();
        pro.getAllProducts();
        pro.writeAllInfo(customers);
        pro.writeAllInfo(products);
    }

    public void createConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING + DB_NAME, USER_NAME, PASSWORD);
            System.out.println("DataBase connection Success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllCustomers(){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer JOIN city ON customer.fk_city_id = city.id");
            while (rs.next()){
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phoneNumber = rs.getInt("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String socialSecurityNumber = rs.getString("social_security_number");
                String address = rs.getString("address");
                City city = rs.getString("city_name");
                int zipCode = rs.getInt("zip_code");

                customers.add(new Customer(firstName,lastName,phoneNumber,email,password,socialSecurityNumber,address,city));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getAllProducts(){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product " +
                    "JOIN color ON product.fk_color_id = color.id " +
                    "JOIN size ON product.fk_size_id = size.id " +
                    "JOIN brand ON product.fk_brand_id = brand.id");

            while (rs.next()){
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                String color = rs.getString("color");
                int sizeEu = rs.getInt("eu");
                int sizeUk = rs.getInt("uk");
                int sizeUs = rs.getInt("us");
                int sizeCm = rs.getInt("cm");
                String brand = rs.getString("brand_name");

                products.add(new Product(productName,priceSek,color,sizeEu,sizeUk,sizeUs,sizeCm,brand));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void writeAllInfo(List<?> list){
        int count = 1;
        for (Object e:list) {
            System.out.println(count + ": " + e);
            count++;
        }
    }


}