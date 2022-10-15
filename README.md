# Mail Attachment Poll

## Configuration

### Gmail

- Enable 2FA
- Generate App password token
- Use token as password

## Usage

### Standalone

#### Create credentials file in same directory

- Create file `credentials.properties`
- Fill in content:

```
username=example@gmail.com
password=password
```

#### Run slim build version

`java -cp "target/mail-attachment-poll-1.0.0.jar:lib/*" dev.heinzl.mailattachmentpoll.ImapExample`

#### Run fat build version

`java -jar mail-attachment-poll-1.0.0-jar-with-dependencies.jar`

### Mirth

- Copy `lib/javax.mail-1.6.2.jar` and `target/mail-attachment-poll-1.0.0.jar` into `custom-lib` folder
- Access with `var receiver = new Packages.dev.heinzl.mailattachmentpoll.DownloadEmailAttachments();`
- IMAP:

```
    receiver.setSaveDirectory("/opt/attachments");
    receiver.setImapMailServerProperties();
    receiver.downloadEmailAttachments("imap.gmail.com", "993", "username", "password");
```

- POP3:

```
    receiver.setSaveDirectory("/opt/attachments");
    receiver.setPop3MailServerProperties();
    receiver.downloadEmailAttachments("pop.gmail.com", "995", "username", "password");
```

## Sources

- https://www.baeldung.com/java-download-email-attachments
- https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-networking-3/src/main/java/com/baeldung/downloadattachments/DownloadEmailAttachments.java

## Things to consider

- POP3 deletes mail from server after reading, but in gmail UI they are still visible - probably because it represents the IMAP behaviour
