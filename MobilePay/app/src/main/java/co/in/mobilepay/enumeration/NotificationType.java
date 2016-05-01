package co.in.mobilepay.enumeration;

public enum NotificationType {
    PURCHASE(1), STATUS(2), CANCEL(3);

    private int notificationType;

    private NotificationType(int notificationType) {
        this.notificationType = notificationType;
    }


    public int getNotificationType() {
        return notificationType;
    }


}
