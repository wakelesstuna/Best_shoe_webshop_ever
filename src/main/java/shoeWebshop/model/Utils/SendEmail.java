package shoeWebshop.model.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

public class SendEmail {

    public SendEmail(String emailAddressToSendTo, String subjectLine, String emailMessage) {

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Credentials.USER.SENDER_EMAIL.toString(), Credentials.USER.SENDER_PASSWORD.toString());

            }
        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Credentials.USER.SENDER_EMAIL.toString()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressToSendTo));
            message.setSubject(subjectLine);
            //message.setText(emailMessage);
            message.setText(buildMessage("Oscar", 3, "Runner xs ", 39, "Nike", 599));

            System.out.println("sending...");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String buildMessage(String customer, int orderSize, String name, int size, String brand, double price) {
        StringBuilder sb = new StringBuilder();
        sb.append("Thank you ");
        sb.append(customer);
        sb.append(" for ordering from Best shoe shop ever!\n");
        sb.append("\n");
        sb.append("Here is you're order:\n");
        sb.append("\n");
        sb.append(String.format("%-28s %-5s %-13s %-3s","Product name", "Size", "Brand", "Price"));
        sb.append("\n");

        double totalprice = 0;
        for (int i = 0; i < orderSize; i++) {
            sb.append(String.format("%-28s %-5d %-13s %-3.2f",name, size, brand, price));
            totalprice += price;
            sb.append("\n");
        }
        sb.append("\n");
        sb.append(String.format("%45s %9.2f","Total price: ", totalprice));
        sb.append("\n");
        sb.append("\n");
        sb.append("If you have any questions about you're order\n");
        sb.append("please contact us on\nnackademinJava20A@gmail.com\n");
        sb.append("\n");
        sb.append("Kind regards Best shoe shop ever!");
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(buildMessage("Oscar", 3, "Runner xs ", 39, "Nike", 599));

    }
}

