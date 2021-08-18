package com.forumias.beta.ui.deta.forumias.notification;

public interface NotificationReadingInteface {
    void notificationReading(int loginUserId , int notificationId , int action , int module ,
                             NotificationModel.MyNotification.NotificationData notificationData);
}
