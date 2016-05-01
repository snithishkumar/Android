package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.enumeration.NotificationType;

/**
 * Created by Nithishkumar on 4/30/2016.
 */
@DatabaseTable(tableName = "NotificationEntity")
public class NotificationEntity {

    public static final String NOTIFICATION_ID = "NotificationId";
    public static final String MESSAGE = "Message";
    public static final String NOTIFICATION_TYPE = "NotificationType";
    public static final String PURCHASE_UUID = "PurchaseUUID";

    @DatabaseField(columnName = NOTIFICATION_ID,generatedId = true)
    private int notificationId;
    @DatabaseField(columnName = MESSAGE)
    private String message;
    @DatabaseField(columnName = NOTIFICATION_TYPE)
    private NotificationType notificationType;
    @DatabaseField(columnName = PURCHASE_UUID)
    private String purchaseGuid;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getPurchaseGuid() {
        return purchaseGuid;
    }

    public void setPurchaseGuid(String purchaseGuid) {
        this.purchaseGuid = purchaseGuid;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "notificationId=" + notificationId +
                ", message='" + message + '\'' +
                ", notificationType=" + notificationType +
                ", purchaseGuid='" + purchaseGuid + '\'' +
                '}';
    }
}
