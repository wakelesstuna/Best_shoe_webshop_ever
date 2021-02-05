package shoeWebshop.model.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
                return new PasswordAuthentication(Credentials.SENDER_EMAIL.toString(), Credentials.SENDER_PASSWORD.toString());

            }
        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Credentials.SENDER_EMAIL.toString()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressToSendTo));
            message.setSubject(subjectLine);
            message.setText(emailMessage);

            System.out.println("sending...");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

