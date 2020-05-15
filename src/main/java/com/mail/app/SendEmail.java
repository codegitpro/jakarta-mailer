package com.mail.app;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

public class SendEmail {

   private static final String SMTP_HOST_NAME = "smtp.blkr.ai.net";
   private static final String SMTP_AUTH_USER = "david";
   private static final String SMTP_AUTH_PWD = "bg,$h93k912,3T";

   public static void main(String args[]) throws Exception {
      String[] recipients = { "christoqian9205@gmail.com" };
      String subject = "subject";
      String message = "first message";
      String from = "mey@gmail.com";

      SendEmail instance = new SendEmail();
      instance.postMail(recipients, subject, message, from);

   }

   public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
      try {
         boolean debug = true;

         Properties props = new Properties();
         props.put("mail.smtp.host", SMTP_HOST_NAME);
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", "25");
         Authenticator auth = new SMTPAuthenticator();
         Session session = Session.getDefaultInstance(props, auth);
         session.setDebug(debug);

         Message msg = new MimeMessage(session);

         InternetAddress addressFrom = new InternetAddress(from);
         msg.setFrom(addressFrom);
         InternetAddress[] addressTo = new InternetAddress[recipients.length];
         for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
         }
         msg.setRecipients(Message.RecipientType.TO, addressTo);

         msg.setSubject(subject);

         msg.setContent(message, "text/plain");

         // Transport transport = session.getTransport();
         // transport.send(msg, SMTP_AUTH_USER, SMTP_AUTH_PWD);
         Transport.send(msg);

      } catch (Throwable e) {
         e.printStackTrace();
      }
   }

   /**
    * SimpleAuthenticator is used to do simple authentication when the SMTP server
    * requires it.
    */
   private class SMTPAuthenticator extends javax.mail.Authenticator {
      public PasswordAuthentication getPasswordAuthentication() {
         String username = SMTP_AUTH_USER;
         String password = SMTP_AUTH_PWD;
         return new PasswordAuthentication(username, password);
      }
   }
}