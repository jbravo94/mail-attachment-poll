package dev.heinzl.mailattachmentpoll;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class ImapExample {
    public static void main(String[] args) {
        String host = "imap.gmail.com";
        String port = "993";

        CredentialsProvider credentialsProvider = new CredentialsProvider();

        String username = credentialsProvider.getUsername();
        String password = credentialsProvider.getPassword();

        String saveDirectory = "attachments";

        DownloadEmailAttachments receiver = new DownloadEmailAttachments();
        receiver.setSaveDirectory(saveDirectory);
        receiver.setImapMailServerProperties();

        try {
            receiver.downloadEmailAttachments(host, port, username, password);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for imap.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
