package dev.heinzl.mailattachmentpoll;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class Pop3Example {
    public static void main(String[] args) {
        String host = "pop.gmail.com";
        String port = "995";

        CredentialsProvider credentialsProvider = new CredentialsProvider();

        String username = credentialsProvider.getUsername();
        String password = credentialsProvider.getPassword();

        String saveDirectory = "attachments";

        DownloadEmailAttachments receiver = new DownloadEmailAttachments();
        receiver.setSaveDirectory(saveDirectory);
        receiver.setPop3MailServerProperties();

        try {
            receiver.downloadEmailAttachments(host, port, username, password);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
