package ch.glastroesch.hades.business.mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    private static final String SMTP_HOST = "smtp.troesch.group";
    private static final String SMTP_PORT = "25";
    private static final String SENDER_ADDRESS = "hades@glastroesch.ch";

    @Async
    public void send(String address, String subject, String text) {

        try {
            Message message = createMessage();
            message.setFrom(new InternetAddress(SENDER_ADDRESS));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            BodyPart messagePart = new MimeBodyPart();
            messagePart.setContent(text, "text/html");
            multipart.addBodyPart(messagePart);
            message.setContent(multipart);

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            Transport.send(message);

        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "error sending mail ", e);
        }

    }

    private Message createMessage() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        Session session = Session.getInstance(properties);

        return new MimeMessage(session);

    }

}
