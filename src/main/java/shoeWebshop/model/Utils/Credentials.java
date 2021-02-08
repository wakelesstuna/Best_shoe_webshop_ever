package shoeWebshop.model.Utils;

import com.sun.mail.smtp.SMTPOutputStream;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Credentials {

    private static String username;
    private static String password;
    private static String databaseName;
    private static String databaseUsername;
    private static String databasePassword;

    public Credentials(){
        Properties login = new Properties();
        try (FileReader in = new FileReader("src/main/java/shoeWebshop/user.properties")){
            login.load(in);
        }catch (IOException e){
            System.out.println("Couldn't read properties");
        }
        username = login.getProperty("username");
        password = login.getProperty("password");
        databaseName = login.getProperty("databaseName");
        databaseUsername = login.getProperty("databaseUser");
        databasePassword = login.getProperty("databasePassword");
    }

    public enum USER {
        SENDER_EMAIL(username),
        SENDER_PASSWORD(password),
        DATABASE_NAME(databaseName),
        DATABASE_USERNAME(databaseUsername),
        DATABASE_PASSWORD(databasePassword);

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



