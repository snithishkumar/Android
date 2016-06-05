package co.in.mobilepay.dao;

import java.sql.SQLException;

import co.in.mobilepay.entity.NotificationEntity;
import co.in.mobilepay.enumeration.NotificationType;

/**
 * Created by Nithishkumar on 4/30/2016.
 */
public interface NotificationDao {

    void createNotification(NotificationEntity notificationEntity)throws SQLException;

    void clearNotification(NotificationType notificationType)throws SQLException;

    long getNotificationCount(NotificationType notificationType)throws SQLException;

    NotificationEntity getNotificationEntity(String purchaseUUID)throws SQLException;

    void updateNotification(NotificationEntity notificationEntity)throws SQLException;
}
