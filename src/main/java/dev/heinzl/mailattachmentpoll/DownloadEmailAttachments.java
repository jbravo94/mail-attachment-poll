package dev.heinzl.mailattachmentpoll;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

public class DownloadEmailAttachments {
    private String downloadDirectory;
    private MailServerPropertiesFunction<String, String, Properties> mailServerProperties;

    public void setSaveDirectory(String dir) {
        this.downloadDirectory = dir;
    }

    public void setPop3MailServerProperties() {
        this.mailServerProperties = (host, port) -> getPop3MailServerProperties(host, port);
    }

    public void setImapMailServerProperties() {
        this.mailServerProperties = (host, port) -> getImapMailServerProperties(host, port);
    }

    public void downloadEmailAttachments(String host, String port, String userName, String password)
            throws NoSuchProviderException, MessagingException, IOException {
        Properties properties = mailServerProperties.apply(host, port);
        Store store = setSessionStoreProperties(userName, password, properties);
        Folder inbox = store.getFolder("INBOX");

        Message[] arrayMessages;

        if ("imap".equals(properties.getProperty("protocol"))) {
            inbox.open(Folder.READ_WRITE);

            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            arrayMessages = inbox.search(ft);
        } else {
            inbox.open(Folder.READ_ONLY);

            arrayMessages = inbox.getMessages();
        }

        for (int i = 0; i < arrayMessages.length; i++) {
            Message message = arrayMessages[i];
            Address[] fromAddress = message.getFrom();
            String from = fromAddress[0].toString();
            String subject = message.getSubject();
            String sentDate = message.getSentDate().toString();
            List<String> attachments = new ArrayList<String>();
            if (message.getContentType().contains("multipart")) {
                attachments = downloadAttachments(message);
            }

            System.out.println("Message #" + (i + 1) + ":");
            System.out.println(" From: " + from);
            System.out.println(" Subject: " + subject);
            System.out.println(" Sent Date: " + sentDate);
            System.out.println(" Attachments: " + attachments);

            if ("imap".equals(properties.getProperty("protocol"))) {
                inbox.setFlags(new Message[] { message }, new Flags(Flags.Flag.SEEN), true);
            }

        }
        inbox.close(false);
        store.close();
    }

    public List<String> downloadAttachments(Message message) throws IOException, MessagingException {
        List<String> downloadedAttachments = new ArrayList<String>();
        Multipart multiPart = (Multipart) message.getContent();
        int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < numberOfParts; partCount++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                String file = part.getFileName();
                String fileName = Instant.now().getEpochSecond() + "_" + file;
                part.saveFile(downloadDirectory + File.separator + fileName);
                downloadedAttachments.add(file);
            }
        }

        return downloadedAttachments;
    }

    private Store setSessionStoreProperties(String userName, String password, Properties properties)
            throws NoSuchProviderException, MessagingException {
        Session session = Session.getDefaultInstance(properties);

        Store store = session.getStore(properties.getProperty("protocol"));
        store.connect(userName, password);
        return store;
    }

    private Properties getPop3MailServerProperties(String host, String port) {
        Properties properties = new Properties();

        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", port);

        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));

        properties.setProperty("protocol", "pop3");

        return properties;
    }

    private Properties getImapMailServerProperties(String host, String port) {
        Properties properties = new Properties();

        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);

        properties.setProperty("mail.imap.ssl.enable", "true");
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", String.valueOf(port));

        properties.setProperty("protocol", "imap");

        return properties;
    }
}