package shoeWebshop.dao;

import javafx.scene.control.Alert;
import shoeWebshop.controllers.FxmlUtils;
import shoeWebshop.model.*;
import shoeWebshop.service.Credentials;
import shoeWebshop.service.SendEmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import static shoeWebshop.service.Credentials.USER.*;

public class Repository extends Credentials {

    private static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(CONNECTION_STRING.toString() + DATABASE_NAME.toString(), DATABASE_USERNAME.toString(), DATABASE_PASSWORD.toString());
            System.out.println("Calling database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //------------------------------------------ CUSTOMER FUNCTIONS ---------------------------------------------------\\

    public static boolean isAuthorizeLogin(String userName, String password) {
        try (Connection con = createConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM sql_shoe_webshop.customer " +
                            "JOIN sql_shoe_webshop.city ON city.id = customer.fk_city_id " +
                            "WHERE customer.email = ? AND aes_decrypt(customer.password,UNHEX(SHA2('" + DECRYPT_KEY + "'," + DECRYPT_VALUE + "))) = ?")){

            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FxmlUtils.isLoggedIn = true;
                FxmlUtils.whoIsLoggedIn = createLoggedInCustomer(rs);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Customer createLoggedInCustomer(ResultSet rs) throws SQLException {
        return new Customer(rs.getInt("id"), rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("phone_number"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("social_security_number"),
                rs.getString("address"),
                new City(rs.getInt(12), rs.getString(13), rs.getInt(14)));
    }

    public static void createNewCustomer(String firstName, String lastName, String phoneNumber, String email, String password, String ssn, String address, String city, int zipCode) {
        try (Connection con = createConnection()){
            if (getAllCustomers().stream().anyMatch(e -> e.getEmail().equals(email))) {
                FxmlUtils.showMessage("Warning", "Couldn't create User", email + " are already in use", Alert.AlertType.INFORMATION);
            } else {
                PreparedStatement pstmt = con.prepareStatement(
                        "INSERT INTO sql_shoe_webshop.customer(first_name, last_name, phone_number, email, password, social_security_number, address, fk_city_id) " +
                                "VALUES (?,?,?,?,AES_ENCRYPT(?,UNHEX(SHA2('" + DECRYPT_KEY + "'," + DECRYPT_VALUE + "))),?,?," +
                                "(SELECT id FROM sql_shoe_webshop.city WHERE city.city_name = ?))");

                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, phoneNumber);
                pstmt.setString(4, email);
                pstmt.setString(5, password);
                pstmt.setString(6, ssn);
                pstmt.setString(7, address);
                pstmt.setString(8, city);

                pstmt.execute();

                SendEmail.sendCreateUserMail(email, "New Customer", firstName + " " + lastName, password);
                FxmlUtils.showMessage("New customer", "Welcome!", "We are so glad that you have join us Mr/Mrs " + firstName, Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<City> getAllCities() {
        List<City> cities = new ArrayList<>();

        try (Connection con = createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sql_shoe_webshop.city;")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String cityName = rs.getString("city_name");
                int zipNumber = rs.getInt("zip_code");

                cities.add(new City(id, cityName, zipNumber));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cities;
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection con = createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM sql_shoe_webshop.customer " +
                            "JOIN sql_shoe_webshop.city ON customer.fk_city_id = city.id")){

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phoneNumber = rs.getInt("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String socialSecurityNumber = rs.getString("social_security_number");
                String address = rs.getString("address");
                City city = new City(rs.getInt("fk_city_id"), rs.getString("city_name"), rs.getInt("zip_code"));

                customers.add(new Customer(id, firstName, lastName, phoneNumber, email, password, socialSecurityNumber, address, city));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    //--------------------------------------------- REVIEW FUNCTIONS ---------------------------------------------------\\

    public static void createNewReview(Product product, int rating, String review) {

        try (Connection con = createConnection();
            CallableStatement cstmt = con.prepareCall("{CALL rate(?,?,(SELECT id FROM sql_shoe_webshop.rating WHERE rating.rating_number = " + rating + "),?)}")){

            cstmt.setInt(1, FxmlUtils.whoIsLoggedIn.getId());
            cstmt.setInt(2, product.getId());
            cstmt.setString(3, review);
            cstmt.execute();

            FxmlUtils.showMessage("Review", "Thank you for you're review", null, Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ReviewObject> getReviewObject(Product product) {
        List<ReviewObject> reviewObjects = new ArrayList<>();

        try (Connection con = createConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT product.id, product.product_name, customer.first_name, customer.last_name, size.eu, rating.rating_number, review, product_review.created FROM sql_shoe_webshop.product_review " +
                    "JOIN sql_shoe_webshop.product ON product.id = product_review.fk_product_id " +
                    "JOIN sql_shoe_webshop.rating ON rating.id = product_review.fk_rating_id " +
                    "JOIN sql_shoe_webshop.size ON size.id = product.fk_size_id " +
                    "JOIN sql_shoe_webshop.customer ON product_review.fk_customer_id = customer.id " +
                    "WHERE product.id =?")){

            pstmt.setInt(1,product.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = (rs.getString("first_name") + " " + rs.getString("last_name"));
                String productName = rs.getString("product_name");
                double size = rs.getDouble("eu");
                double rating = rs.getDouble("rating_number");
                String review = rs.getString("review");
                Date date = rs.getDate("created");

                reviewObjects.add(new ReviewObject(id, customerName, productName, size, rating, review, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewObjects;
    }


    //--------------------------------------------- ORDERS FUNCTIONS ---------------------------------------------------\\


    public static int createNewOrder(Customer customer) {
        int orderId = 0;
        try (Connection con = createConnection();
             CallableStatement cstmt = con.prepareCall("{? = CALL new_order(?)}")){

            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setInt(2, customer.getId());
            cstmt.execute();
            orderId = cstmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public static List<Orders> getCustomerOrders(Customer customer) {
        List<Orders> orders = new ArrayList<>();

        try (Connection con = createConnection();
            PreparedStatement pstmt = con.prepareCall("SELECT orders.id, orders.date,count(orders_product.fk_product_id) AS totalShoes, sum(product_price) AS totalPrice " +
                    "FROM sql_shoe_webshop.orders " +
                    "JOIN sql_shoe_webshop.orders_product ON orders.id = orders_product.fk_orders_id " +
                    "JOIN sql_shoe_webshop.customer ON customer.id = orders.fk_customer_id " +
                    "WHERE customer.id = ? " +
                    "GROUP BY orders.id")){

            pstmt.setInt(1,customer.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(new Orders(rs.getInt("id"),
                        rs.getDate("date"),
                        customer,
                        rs.getDouble("totalPrice"),
                        rs.getInt("totalShoes")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public static List<Product> getSelectedOrder(Orders order) {
        List<Product> products = new ArrayList<>();

        try (Connection con = createConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM sql_shoe_webshop.product " +
                    "JOIN sql_shoe_webshop.orders_product ON product.id = orders_product.fk_product_id " +
                    "JOIN sql_shoe_webshop.orders ON orders.id = orders_product.fk_orders_id " +
                    "JOIN sql_shoe_webshop.color ON product.fk_color_id = color.id " +
                    "JOIN sql_shoe_webshop.size ON product.fk_size_id = size.id " +
                    "JOIN sql_shoe_webshop.brand ON product.fk_brand_id = brand.id " +
                    "WHERE orders_product.fk_orders_id = ?")){

            pstmt.setInt(1,order.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                Color color = new Color(rs.getInt("fk_color_id"), rs.getString("color"));
                Size size = new Size(rs.getInt("fk_size_id"), rs.getDouble("eu"), rs.getDouble("uk"), rs.getDouble("us"), rs.getDouble("cm"));
                int amountOrdered = rs.getInt("quantity");
                Brand brand = new Brand(rs.getInt("fk_brand_id"), rs.getString("brand_name"));

                products.add(new Product(id, productName, priceSek, color, size, amountOrdered, brand));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException ignored){
        }
        return products;
    }


    public static void discardOrder(int orderId){
        if (FxmlUtils.orderCreatedButNotSent){
            try(Connection con = createConnection();
                CallableStatement cstmt = con.prepareCall("{CALL discard_order(?)}")){

                cstmt.setInt(1,orderId);
                cstmt.execute();

            }catch (SQLException e){
                System.out.println("Ingen pågående order att avsluta");
            }
        }
    }

    //--------------------------------------------- PRODUCT FUNCTIONS ---------------------------------------------------\\

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection con = createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sql_shoe_webshop.product " +
                    "JOIN sql_shoe_webshop.color ON product.fk_color_id = color.id " +
                    "JOIN sql_shoe_webshop.size ON product.fk_size_id = size.id " +
                    "JOIN sql_shoe_webshop.brand ON product.fk_brand_id = brand.id; ")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                Color color = new Color(rs.getInt("fk_color_id"), rs.getString("color"));
                Size size = new Size(rs.getInt("fk_size_id"), rs.getDouble("eu"), rs.getDouble("uk"), rs.getDouble("us"), rs.getDouble("cm"));
                Brand brand = new Brand(rs.getInt("fk_brand_id"), rs.getString("brand_name"));
                int stock = Integer.parseInt(rs.getString("stock"));

                products.add(new Product(id, productName, priceSek, color, size, brand, stock));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return products;
    }

    public static List<Product> getProductsOfCategory(String categoryName){
        List<Product> products = new ArrayList<>();

        try (Connection con = createConnection();
            PreparedStatement pstmt = con.prepareCall("SELECT * FROM sql_shoe_webshop.product " +
                    "JOIN sql_shoe_webshop.color ON product.fk_color_id = color.id " +
                    "JOIN sql_shoe_webshop.size ON product.fk_size_id = size.id " +
                    "JOIN sql_shoe_webshop.brand ON product.fk_brand_id = brand.id " +
                    "JOIN sql_shoe_webshop.product_category ON product_category.fk_product_id = product.id " +
                    "JOIN sql_shoe_webshop.category ON product_category.fk_category_id = category.id " +
                    "WHERE category_name = ?")){

            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                Color color = new Color(rs.getInt("fk_color_id"), rs.getString("color"));
                Size size = new Size(rs.getInt("fk_size_id"), rs.getDouble("eu"), rs.getDouble("uk"), rs.getDouble("us"), rs.getDouble("cm"));
                Brand brand = new Brand(rs.getInt("fk_brand_id"), rs.getString("brand_name"));
                int stock = Integer.parseInt(rs.getString("stock"));

                products.add(new Product(id, productName, priceSek, color, size, brand, stock));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return products;
    }

    public static List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();

        try (Connection con = createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sql_shoe_webshop.category")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String categoryName = rs.getString("category_name");

                categories.add(new Category(id,categoryName));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return categories;
    }

    //--------------------------------------------- CART FUNCTIONS ---------------------------------------------------\\


    public static void addToCart(Integer currentCustomerOrder, Customer whoIsLoggedIn, int id) {
        try (Connection con = createConnection();
            CallableStatement cstmt = con.prepareCall("{CALL addToCart(?,?,?)}")){

            cstmt.setInt(1, currentCustomerOrder);
            cstmt.setInt(2, whoIsLoggedIn.getId());
            cstmt.setInt(3, id);
            cstmt.execute();

            FxmlUtils.showMessage("Added to cart", "New item added to your cart", null, Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            if (!FxmlUtils.isLoggedIn){
                FxmlUtils.showMessage("Not logged in!", "You need to be logged in\nto make and order", null, Alert.AlertType.ERROR);
            }else {
                FxmlUtils.showMessage("No Order", "You need to create a order first,\nbefore you can add products", null, Alert.AlertType.ERROR);
            }

        }

    }

    public static void removeFromCart(Integer currentCustomerOrder, Customer whoIsLoggedIn, int id) {

        try (Connection con = createConnection();
            CallableStatement cstmt = con.prepareCall("{CALL remove_form_cart(?,?,?)}")){

            cstmt.setInt(1, currentCustomerOrder);
            cstmt.setInt(2, whoIsLoggedIn.getId());
            cstmt.setInt(3, id);
            cstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Credentials();
        getCitiesOfSweden();
    }

    public static void getCitiesOfSweden(){

        try {
            URL url = new URL("https://postnummerapi.se/api/1.0/get/13642/");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String input;
            while ((input = in.readLine()) != null){
                System.out.println(input);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}