package dev.heinzl.mailattachmentpoll;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class ImapExample {
    public static void main(String[] args) {
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "your_email";
        String password = "your_password";

        String saveDirectory = "attachments";

        DownloadEmailAttachments receiver = new DownloadEmailAttachments();
        receiver.setSaveDirectory(saveDirectory);
        receiver.setImapMailServerProperties();
        try {
            receiver.downloadEmailAttachments(host, port, userName, password);
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
