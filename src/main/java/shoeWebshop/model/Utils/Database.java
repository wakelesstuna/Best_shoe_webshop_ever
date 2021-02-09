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

    private static List<Customer> customers = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static List<Brand> brand = new ArrayList<>();
    private static List<Category> category = new ArrayList<>();
    private static List<Color> color = new ArrayList<>();
    private static List<City> citys = new ArrayList<>();

    // for testing database methods
    public static void main(String[] args) {
        Credentials c = new Credentials();
        getAllCustomers().forEach(System.out::println);
    }

    public static void createConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING.toString() + DATABASE_NAME.toString(), DATABASE_USERNAME.toString(), DATABASE_PASSWORD.toString());
            System.out.println("DataBase connection Success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isAuthorizeLogin(String userName, String password) {
        createConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customer JOIN city ON city.id = customer.fk_city_id WHERE customer.email = ? AND aes_decrypt(customer.password,UNHEX(SHA2('" + DECRYPT_KEY + "'," + DECRYPT_VALUE + "))) = ?");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FxmlUtils.showMessage("Logged in", "Logged in", "Logged in sucsses", Alert.AlertType.INFORMATION);
                FxmlUtils.isLoggedIn = true;
                createLoggedInCustomer(rs);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FxmlUtils.showMessage("Warning", "Not logged in", "Wrong username or password", Alert.AlertType.ERROR);
        return false;
    }

    private static void createLoggedInCustomer(ResultSet rs) throws SQLException {
        FxmlUtils.whoIsLoggedIn = new Customer(rs.getInt(1),rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("phone_number"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("social_security_number"),
                rs.getString("address"),
                new City(rs.getInt(12), rs.getString(13), rs.getInt(14)));
        // TODO: 2021-02-09 Lägg till så man får rätt city, hämta id, namn, och zip code
        System.out.println(FxmlUtils.whoIsLoggedIn);
    }

    public static void createNewCustomer(String firstName, String lastName, String phoneNumber, String email, String password, String ssn, String address, String city, int zipCode) {
        createConnection();
        try {
            if (getAllCustomers().stream().anyMatch(e -> e.getEmail().equals(email))) {
                FxmlUtils.showMessage("Warning", "Couldn't create User", email + " are already in use", Alert.AlertType.INFORMATION);
            } else {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO customer(first_name, last_name, phone_number, email, password, social_security_number, address, fk_city_id) VALUES (?,?,?,?,AES_ENCRYPT(?,UNHEX(SHA2('" + DECRYPT_KEY + "'," + DECRYPT_VALUE + "))),?,?,(SELECT id FROM city WHERE city.city_name = '" + city + "'))");
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

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer JOIN city ON customer.fk_city_id = city.id");
            while (rs.next()) {
                int id = rs.getInt(1);
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phoneNumber = rs.getInt("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String socialSecurityNumber = rs.getString("social_security_number");
                String address = rs.getString("address");
                int city_id = rs.getInt(12);
                String city = rs.getString("city_name");
                int zipCode = rs.getInt("zip_code");

                customers.add(new Customer(id,firstName, lastName, phoneNumber, email, password, socialSecurityNumber, address, new City(city_id, city, zipCode)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public static void createNewReview(Product product, int rating, String review) {
        createConnection();
        try {
            System.out.println(product.getId() + " " +  FxmlUtils.whoIsLoggedIn.getId() + " " + review);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO product_review (fk_product_id,fk_customer_id,fk_rating_id, review) VALUES (?,?,1,?)");
            // (SELECT id FROM rating WHERE rating.rating_number = " + rating + ")
            stmt.setInt(1, product.getId());
            stmt.setInt(2, FxmlUtils.whoIsLoggedIn.getId());
            stmt.setString(3,review);
            stmt.execute();

            FxmlUtils.showMessage("Review", "Thank you for you're review", null, Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM city;");
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

    public void getAllProducts() {
        createConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product " +
                    "JOIN color ON product.fk_color_id = color.id " +
                    "JOIN size ON product.fk_size_id = size.id " +
                    "JOIN brand ON product.fk_brand_id = brand.id");

            while (rs.next()) {
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");
                String color = rs.getString("color");
                int sizeEu = rs.getInt("eu");
                int sizeUk = rs.getInt("uk");
                int sizeUs = rs.getInt("us");
                int sizeCm = rs.getInt("cm");
                String brand = rs.getString("brand_name");

                //products.add(new Product(productName,priceSek,color,sizeEu,sizeUk,sizeUs,sizeCm,brand));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void writeAllInfo(List<?> list) {
        int count = 1;
        for (Object e : list) {
            System.out.println(count + ": " + e);
            count++;
        }
    }
}