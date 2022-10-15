package dev.heinzl.mailattachmentpoll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CredentialsProvider {

    private String username;
    private String password;

    public CredentialsProvider() {
        try (InputStream input = new FileInputStream("credentials.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            this.username = prop.getProperty("username");
            this.password = prop.getProperty("password");

        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
