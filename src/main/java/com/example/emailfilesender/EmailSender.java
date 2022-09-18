package com.example.emailfilesender;

import jakarta.servlet.ServletContext;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    private final String user;
    private final String password;
    private final String host;
    private final String port;

    public EmailSender(ServletContext context) {
        user = context.getInitParameter("user");
        password = context.getInitParameter("password");
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
    }


    public void sendEmail(String toEmail, String subject, String body)
            throws MessagingException, IOException {

        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

        message.setSubject(subject);

        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }

    public void sendEmail(String toEmail, String subject, String body, File file)
            throws MessagingException, IOException {

        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

        message.setSubject(subject);


        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);
        MimeBodyPart fileBodyPart = new MimeBodyPart();
        fileBodyPart.attachFile(file);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(fileBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    private Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };

        return Session.getInstance(properties, auth);
    }
}
