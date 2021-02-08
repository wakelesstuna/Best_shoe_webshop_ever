package shoeWebshop.model.Utils;

import shoeWebshop.model.Product;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class SendEmail {

    private static Session connectToMailServer(){
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Credentials.USER.SENDER_EMAIL.toString(), Credentials.USER.SENDER_PASSWORD.toString());
            }
        });
    }

    public static void sendOrderConfirmMail(String emailAddressToSendTo, String subjectLine, List<Product> list, String customerName){
        Session session = connectToMailServer();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Credentials.USER.SENDER_EMAIL.toString()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressToSendTo));
            message.setSubject(subjectLine);
            message.setText(buildOrderConfirmMail(list,customerName));

            System.out.println("sending...");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendCreateUserMail(String emailAddressToSendTo, String subjectLine, String customerName, String customerPassword ){
        Session session = connectToMailServer();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Credentials.USER.SENDER_EMAIL.toString()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressToSendTo));
            message.setSubject(subjectLine);
            message.setText(buildCreateCustomerMail(customerName,emailAddressToSendTo,customerPassword));

            System.out.println("sending...");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String buildCreateCustomerMail(String customerName, String customerEmail, String customerPassword){
        StringBuilder sb = new StringBuilder();

        return sb.append("Welcome to the Best Shoe Shop Ever!\n")
                .append("We are so glad that you have join us Mr/Mrs ")
                .append(customerName)
                .append("! \nWe hope that you will enjoy shopping at our store and be happy about the products you purchases\n\n")
                .append("Here is your username: ")
                .append(customerEmail)
                .append("\nand here is your password: ")
                .append(customerPassword)
                .append("\n\nIf you have any questions just let us know at\n")
                .append("nackademinJava20A@gmail.com")
                .append("\n")
                .append("Kind regards Best shoe shop ever!").toString();

    }

    private static String buildOrderConfirmMail(List<Product> list, String customerName) {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you ")
        .append(customerName)
        .append(" for ordering from Best shoe shop ever!\n")
        .append("\n")
        .append("Here is you're order:\n")
        .append("\n")
        .append(String.format("%-28s %-5s %-13s %-3s","Product name", "Size", "Brand", "Price"))
        .append("\n");

        double totalprice = 0;
        for (Product product : list) {
            sb.append(String.format("%-28s %-5d %-13s %-3.2f",product.getProductName(), product.getSize(), product.getBrand(), product.getPriceSek()))
            .append("\n");
            totalprice += product.getPriceSek();
        }
        sb.append("\n")
        .append(String.format("%45s %9.2f","Total price: ", totalprice))
        .append("\n")
        .append("\n")
        .append("If you have any questions about you're order\n")
        .append("please contact us on\nnackademinJava20A@gmail.com\n")
        .append("\n")
        .append("Kind regards Best shoe shop ever!");

        return sb.toString();
    }
}

