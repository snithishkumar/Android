package co.in.mobilepay.dao.impl;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;

import co.in.mobilepay.dao.NotificationDao;
import co.in.mobilepay.entity.NotificationEntity;
import co.in.mobilepay.enumeration.NotificationType;

/**
 * Created by Nithishkumar on 4/30/2016.
 */
public class NotificationDaoImpl  extends BaseDaoImpl implements NotificationDao{

    Dao<NotificationEntity,Integer> dao = null;

    public NotificationDaoImpl(Context context) throws SQLException {
        super(context);
        initDao();
    }

    @Override
    protected void initDao() throws SQLException {
        try{
            dao = databaseHelper.getDao(NotificationEntity.class);
        }catch (Exception e){
            e.printStackTrace();

        }

    }



    public void createNotification(NotificationEntity notificationEntity)throws SQLException{
        dao.create(notificationEntity);
    }

    public void clearNotification(NotificationType notificationType)throws SQLException{
        DeleteBuilder<NotificationEntity,Integer> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(NotificationEntity.NOTIFICATION_TYPE,notificationType);
        deleteBuilder.delete();
    }


    public long getNotificationCount(NotificationType notificationType)throws SQLException{
       return dao.queryBuilder().where().eq(NotificationEntity.NOTIFICATION_TYPE,notificationType).countOf();
    }


    public NotificationEntity getNotificationEntity(String purchaseUUID)throws SQLException{
        return dao.queryBuilder().where().eq(NotificationEntity.PURCHASE_UUID,purchaseUUID).queryForFirst();
    }


    public void updateNotification(NotificationEntity notificationEntity)throws SQLException{
        dao.update(notificationEntity);
    }

}
