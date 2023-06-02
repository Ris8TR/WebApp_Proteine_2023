package it.unical.mat.progettoweb2023.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class Registration {
    private static final int CODE_LENGTH = 6; // Lunghezza del codice di conferma
    private static final String EMAIL_SUBJECT = "Conferma registrazione";
    private static final String EMAIL_BODY = "Il tuo codice di conferma è: ";

    public void registerUser(String email) {
        // Genera un codice di conferma casuale
        String confirmationCode = generateConfirmationCode();

        // Invia l'email di conferma
        sendConfirmationEmail(email, confirmationCode);
    }

    private String generateConfirmationCode() {
        // Genera un codice alfanumerico casuale
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }

    private void sendConfirmationEmail(String email, String code) {
        // Configurazione delle proprietà per JavaMail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");


        // Credenziali per l'autenticazione SMTP
        final String username = "lunapuzzo@gmail.com";
        final String password = "03Al6472";

        // Crea una sessione di posta
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crea un oggetto MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Imposta il mittente
            message.setFrom(new InternetAddress("lunapuzzo@gmail.com.com"));

            // Imposta il destinatario
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // Imposta l'oggetto dell'email
            message.setSubject(EMAIL_SUBJECT);

            // Crea il contenuto del messaggio
            String emailContent = EMAIL_BODY + code;
            message.setText(emailContent);

            // Invia l'email
            Transport.send(message);

            System.out.println("Email inviata con successo a " + email);
        } catch (MessagingException e) {
            System.out.println("Si è verificato un errore durante l'invio dell'email: " + e.getMessage());
        }
    }
}
