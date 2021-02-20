package shoeWebshop.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Credentials {

    private static String username;
    private static String password;
    private static String connectionString;
    private static String databaseName;
    private static String databaseUsername;
    private static String databasePassword;
    private static String decryptKey;
    private static String decryptValue;

    public Credentials() {
        Properties prop = new Properties();
        try (FileReader file = new FileReader("src/main/resources/setting.properties")) {
            prop.load(file);
        } catch (IOException e) {
            System.out.println("Couldn't read properties");
        }
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        connectionString = prop.getProperty("connectionString");
        databaseName = prop.getProperty("databaseName");
        databaseUsername = prop.getProperty("databaseUser");
        databasePassword = prop.getProperty("databasePassword");
        decryptKey = prop.getProperty("decryptkey");
        decryptValue = prop.getProperty("decryptvalue");
    }

    public enum USER {
        SENDER_EMAIL(username),
        SENDER_PASSWORD(password),
        CONNECTION_STRING(connectionString),
        DATABASE_NAME(databaseName),
        DATABASE_USERNAME(databaseUsername),
        DATABASE_PASSWORD(databasePassword),
        DECRYPT_KEY(decryptKey),
        DECRYPT_VALUE(decryptValue);

        private final String s;

        USER(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }

    }
}



