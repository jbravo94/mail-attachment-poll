package dev.heinzl.mailattachmentpoll;

@FunctionalInterface
public interface MailServerPropertiesFunction<H, P, R> {
    public R apply(H h, P p);
}