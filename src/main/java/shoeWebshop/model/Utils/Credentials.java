package shoeWebshop.model.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Credentials {

    private static String username;
    private static String password;

    public Credentials(){
        Properties login = new Properties();
        try (FileReader in = new FileReader("src/main/java/shoeWebshop/user.properties")){
            login.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
        username = login.getProperty("username");
        password = login.getProperty("password");
    }

    public enum USER {
        SENDER_EMAIL(username),
        SENDER_PASSWORD(password);

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



