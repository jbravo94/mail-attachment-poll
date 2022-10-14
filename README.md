# Mail Attachment Poll

# Configuration

## Gmail

- Enable 2FA
- Generate App token
  ...

# Usage

## Run slim build version

`java -cp "target/mail-attachment-poll-1.0-SNAPSHOT.jar:lib/*" dev.heinzl.mailattachmentpoll.App`

## Run fat build version

`java -jar mail-attachment-poll-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Mirth

- Copy `lib/javax.mail-1.6.2.jar` and `target/mail-attachment-poll-1.0-SNAPSHOT.jar` into `custom-lib` folder.
- Access with `var downloadEmailAttachments = new Packages.dev.heinzl.mailattachmentpoll.DownloadEmailAttachments();`
  ...

# Sources

- https://www.baeldung.com/java-download-email-attachments
- https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-networking-3/src/main/java/com/baeldung/downloadattachments/DownloadEmailAttachments.java

# TODO

- change to imap
- test with gmail account
- test in mirth
- export example mirth channel with periodic javascript
- test if javax.mail not already in mirth available
