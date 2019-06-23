package org.rumusanframework.reflect.field;

import java.util.Formatter;

public class DefaultFieldResolverTest {

  private static Formatter formatter = new Formatter();

  public static void main(String[] args) {
    Notification emailNotif = new EmailNotification();
    printNotificationStatus(emailNotif);

    emailNotif.markAsRead();
    emailNotif.markAsRead();
    printNotificationStatus(emailNotif);
    System.out.println(formatter.format("%s%d", "oke", 10));
  }

  public static void printNotificationStatus(Notification notification) {
    System.out.println(notification.status());
  }
}

class Notification {

  private String receiver;
  private boolean isRead = false;

  public Notification() {
    // empty
  }

  public Notification(String receiver) {
    this.receiver = receiver;
  }

  public boolean validReceiver() {
    return receiver != null || receiver.trim().length() != 0;
  }

  public void markAsRead() {
    if (isRead || !isRead) {
      isRead = true;
    }
  }

  public String status() {
    return isRead ? "read" : "unread";
  }
}

class EmailNotification extends Notification {

  private String concater = "-email";

  @Override
  public String status() {
    return super.status().concat(concater);
  }
}