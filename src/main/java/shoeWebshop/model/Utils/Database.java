package shoeWebshop.model.Utils;

import javafx.scene.control.Alert;
import shoeWebshop.controllers.FxmlUtils;
import shoeWebshop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import static shoeWebshop.model.Utils.Credentials.USER.*;

public class Database extends Credentials {

    private static Connection connection;

    public static void createConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING.toString() + DATABASE_NAME.toString(), DATABASE_USERNAME.toString(), DATABASE_PASSWORD.toString());
            System.out.println("DataBase connection Success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------ CUSTOMER FUNCTIONS ---------------------------------------------------\\

    public static boolean isAuthorizeLogin(String userName, String password) {
        createConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM sql_shoe_webshop.customer " +
                            "JOIN sql_shoe_webshop.city ON city.id = customer.fk_city_id " +
                            "WHERE customer.email = ? AND aes_decrypt(customer.password,UNHEX(SHA2('" + DECRYPT_KEY + "'," + DECRYPT_VALUE + "))) = ?");
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
        createConnection();
        try {
            if (getAllCustomers().stream().anyMatch(e -> e.getEmail().equals(email))) {
                FxmlUtils.showMessage("Warning", "Couldn't create User", email + " are already in use", Alert.AlertType.INFORMATION);
            } else {
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO sql_shoe_webshop.customer(first_name, last_name, phone_number, email, password, social_security_number, address, fk_city_id) " +
                                "VALUES (?,?,?,?,AES_ENCRYPT(?,UNHEX(SHA2('" + DECRYPT_KEY + "'," + DECRYPT_VALUE + "))),?,?," +
                                "(SELECT id FROM sql_shoe_webshop.city WHERE city.city_name = '" + city + "'))");
                // TODO: 2021-02-15 fixa så man lägger in zipCode också

                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, phoneNumber);
                stmt.setString(4, email);
                stmt.setString(5, password);
                stmt.setString(6, ssn);
                stmt.setString(7, address);

                stmt.execute();

                SendEmail.sendCreateUserMail(email, "New Customer", firstName + " " + lastName, password);
                FxmlUtils.showMessage("New customer", "Welcome!", "We are so glad that you have join us Mr/Mrs " + firstName, Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sql_shoe_webshop.city;");
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
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM sql_shoe_webshop.customer " +
                            "JOIN sql_shoe_webshop.city ON customer.fk_city_id = city.id");
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
        createConnection();
        try {
            CallableStatement cstmt = connection.prepareCall("{CALL rate(?,?,(SELECT id FROM sql_shoe_webshop.rating WHERE rating.rating_number = " + rating + "),?)}");

            cstmt.setInt(1, FxmlUtils.whoIsLoggedIn.getId());
            cstmt.setInt(2, product.getId());
            cstmt.setString(3, review);
            cstmt.execute();

            System.out.println("Calling Stored procedure rate from database");

            FxmlUtils.showMessage("Review", "Thank you for you're review", null, Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ReviewObject> getReviewObject(Product product) {
        createConnection();
        List<ReviewObject> reviewObjects = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT product.id, product.product_name, customer.first_name, customer.last_name, size.eu, rating.rating_number, review FROM sql_shoe_webshop.product_review " +
                    "JOIN sql_shoe_webshop.product ON product.id = product_review.fk_product_id " +
                    "JOIN sql_shoe_webshop.rating ON rating.id = product_review.fk_rating_id " +
                    "JOIN sql_shoe_webshop.size ON size.id = product.fk_size_id " +
                    "JOIN sql_shoe_webshop.customer ON product_review.fk_customer_id = customer.id " +
                    "WHERE product.id =" + product.getId() + "");
            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = (rs.getString("first_name") + " " + rs.getString("last_name"));
                String productName = rs.getString("product_name");
                double size = rs.getDouble("eu");
                double rating = rs.getDouble("rating_number");
                String review = rs.getString("review");

                reviewObjects.add(new ReviewObject(id, customerName, productName, size, rating, review));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewObjects;
    }


    //--------------------------------------------- ORDERS FUNCTIONS ---------------------------------------------------\\

    public static List<Orders> getCustomerOrders(Customer customer) {
        createConnection();
        List<Orders> orders = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT orders.id, orders.date,count(orders_product.fk_product_id) AS totalShoes, sum(product_price) AS totalPrice " +
                    "FROM sql_shoe_webshop.orders " +
                    "JOIN sql_shoe_webshop.orders_product ON orders.id = orders_product.fk_orders_id " +
                    "JOIN sql_shoe_webshop.customer ON customer.id = orders.fk_customer_id " +
                    "WHERE customer.id = " + customer.getId() + " " +
                    "GROUP BY orders.id");

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
        createConnection();
        List<Product> products = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sql_shoe_webshop.product " +
                    "JOIN sql_shoe_webshop.orders_product ON product.id = orders_product.fk_product_id " +
                    "JOIN sql_shoe_webshop.orders ON orders.id = orders_product.fk_orders_id " +
                    "JOIN sql_shoe_webshop.color ON product.fk_color_id = color.id " +
                    "JOIN sql_shoe_webshop.size ON product.fk_size_id = size.id " +
                    "JOIN sql_shoe_webshop.brand ON product.fk_brand_id = brand.id " +
                    "WHERE orders_product.fk_orders_id = " + order.getId() + " ");

            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                Color color = new Color(rs.getInt("fk_color_id"), rs.getString("color"));
                Size size = new Size(rs.getInt("fk_size_id"), rs.getDouble("eu"));
                int amountOrdered = rs.getInt("quantity");
                Brand brand = new Brand(rs.getInt("fk_brand_id"), rs.getString("brand_name"));

                products.add(new Product(id, productName, priceSek, color, size, amountOrdered, brand));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    public static List<Product> getAllProducts() {
        createConnection();
        List<Product> products = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sql_shoe_webshop.product " +
                    "JOIN sql_shoe_webshop.color ON product.fk_color_id = color.id " +
                    "JOIN sql_shoe_webshop.size ON product.fk_size_id = size.id " +
                    "JOIN sql_shoe_webshop.brand ON product.fk_brand_id = brand.id " +
                    "JOIN sql_shoe_webshop.product_category ON product.id = product_category.fk_product_id " +
                    "JOIN sql_shoe_webshop.category ON product_category.fk_category_id = category.id");

            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                Color color = new Color(rs.getInt("fk_color_id"), rs.getString("color"));
                Size size = new Size(rs.getInt("fk_size_id"), rs.getDouble("eu"));
                Brand brand = new Brand(rs.getInt("fk_brand_id"), rs.getString("brand_name"));
                Category category = new Category(rs.getInt("fk_category_id"), rs.getString("category_name"));
                int stock = Integer.parseInt(rs.getString("stock"));

                products.add(new Product(id, productName, priceSek, color, size, brand, category, stock));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return products;
    }

    //--------------------------------------------- CART FUNCTIONS ---------------------------------------------------\\

    public static int createNewOrder(Customer customer) {
        createConnection();
        int orderId = 0;
        try {
            CallableStatement cstmt = connection.prepareCall("{? = CALL new_order(?)}");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setInt(2, customer.getId());
            cstmt.execute();
            orderId = cstmt.getInt(1);

            System.out.println("Calling function new_order");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }


    public static void addToCart(Integer currentCustomerOrder, Customer whoIsLoggedIn, int id) {
        createConnection();
        System.out.println("database SP add to cart");
        try {
            CallableStatement cstmt = connection.prepareCall("{CALL addToCart(?,?,?)}");
            cstmt.setInt(1, currentCustomerOrder);
            cstmt.setInt(2, whoIsLoggedIn.getId());
            cstmt.setInt(3, id);
            cstmt.execute();

            System.out.println("Calling Stored procedure addToCart");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeFromCart(Integer currentCustomerOrder, Customer whoIsLoggedIn, int id) {
        createConnection();
        System.out.println("database SP remove from cart");
        try {
            CallableStatement cstmt = connection.prepareCall("{CALL remove_form_cart(?,?,?)}");
            cstmt.setInt(1, currentCustomerOrder);
            cstmt.setInt(2, whoIsLoggedIn.getId());
            cstmt.setInt(3, id);
            cstmt.execute();

            System.out.println("Calling Stored procedure remove_from_cart");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}