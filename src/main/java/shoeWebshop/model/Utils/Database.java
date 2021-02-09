package shoeWebshop.model.Utils;

import javafx.scene.control.Alert;
import shoeWebshop.controllers.FxmlUtils;
import shoeWebshop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;

import static shoeWebshop.model.Utils.Credentials.USER.*;

public class Database extends Credentials {

    private static Connection connection;

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
                int id = Integer.parseInt(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phoneNumber = rs.getInt("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String socialSecurityNumber = rs.getString("social_security_number");
                String address = rs.getString("address");
                City city = City.getCity(rs.getInt("fk_city_id"));

                customers.add(new Customer(id, firstName, lastName, phoneNumber, email, password, socialSecurityNumber, address, city));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
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
    Orders ordersId;
    Product productId;
    double productPrice; // denna ska ta värdet ifrån product klassen när den skapas
    int quantity;

    public static List<OrdersProduct> getOrderProduct() {
        createConnection();
        List<OrdersProduct> ordersProducts = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders_product ");

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                int orders_id = (rs.getInt("fk_orders_id"));
                Orders ordersId = getOrders().get(orders_id);

                int product_id = (rs.getInt("fk_product_id"));
                Product productId= Product.getProduct(product_id);

                double productPrice =(rs.getDouble("product_price"));
                int quantity = (rs.getInt("quantity"));
                ordersProducts.add(new OrdersProduct(id, ordersId, productId, productPrice, quantity));

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return ordersProducts;
    }

    public static List<ProductCategory> getProductCategory() {
        createConnection();
        List<ProductCategory> productCategories = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product_category");

            while (rs.next()) {
                int id = (rs.getInt("id"));
                int cId = (rs.getInt("fk_category_id"));
                Category categoryId = Category.getCategory(cId);

                int product_id = (rs.getInt("fk_product_id"));
                Product productId= Product.getProduct(product_id);

                productCategories.add(new ProductCategory(id, categoryId, productId));

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return productCategories;
    }

    public static List<Rate> getRates() {
        createConnection();
        List<Rate> ratings = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rating");

            while (rs.next()) {
                int id = (rs.getInt("id"));
                String ratingText = (rs.getString("rating_text"));
                int ratingNumber = (rs.getInt("rating_number"));

                ratings.add(new Rate(id, ratingText, ratingNumber));

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return ratings;
    }

    public static List<ProductRate> getProductRatings() {
        createConnection();
        List<ProductRate> productRatings = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product_review");

            while (rs.next()) {
                int id = (rs.getInt("id"));

                int product_id = (rs.getInt("fk_product_id"));
                Product productId= Product.getProduct(product_id);

                int cId = (rs.getInt("fk_customer_id"));
                Customer customer = Customer.getCustomer(cId);

                int rId = (rs.getInt("fk_rating_id"));
                Rate rating = Rate.getRatings(rId);

                String review = (rs.getString("review"));


                productRatings.add(new ProductRate(id, productId, customer,rating, review  ));

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return productRatings;
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

    public static List<City> getCitys() {
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

    public static List<Category> getCategorys() {
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

    public static List<Product> getAllProducts() {
        createConnection();
        List<Product> products = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product " +
                    "JOIN color ON product.fk_color_id = color.id " +
                    "JOIN size ON product.fk_size_id = size.id " +
                    "JOIN brand ON product.fk_brand_id = brand.id");

            while (rs.next()){
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double priceSek = rs.getDouble("price_sek");

                Color color = Color.getColor(rs.getInt("fk_color_id"));
                Size size = Size.getSize(rs.getInt("fk_size_id"));
                Brand brand = Brand.getBrand(rs.getInt("fk_brand_id"));
                int stock = Integer.parseInt(rs.getString("stock"));
                products.add(new Product(id, productName, priceSek, color, size, brand, stock));
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