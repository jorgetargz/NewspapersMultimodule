package jakarta.beans.utils;

import configuration.Configuration;
import jakarta.common.Constantes;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Properties;


@Log4j2
public class MailSender implements Serializable {

    private final Configuration configuration;

    @Inject
    public MailSender(Configuration configuration) {
        this.configuration = configuration;
    }

    public void generateAndSendEmail(String to, String emailBody, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        //Configure mail Server
        mailServerProperties = System.getProperties();
        mailServerProperties.put(Constantes.MAIL_SMTP_PORT, Integer.parseInt(configuration.getMailPort()));
        mailServerProperties.put(Constantes.MAIL_SMTP_AUTH, configuration.getMailAuth());
        mailServerProperties.put(Constantes.MAIL_SMTP_SSL_TRUST, configuration.getMailHost());
        mailServerProperties.put(Constantes.MAIL_SMTP_STARTTLS_ENABLE, configuration.getMailStarttls());

        //Write email
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(emailBody, Constantes.TEXT_HTML);

        //Send email
        Transport transport = getMailSession.getTransport(Constantes.SMTP);
        transport.connect(configuration.getMailHost(),
                configuration.getMailUsername(),
                configuration.getMailPassword());

        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
