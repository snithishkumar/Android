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

    public NotificationType getNotificationType(int notificationType){
        switch (notificationType){
            case 1:
                return PURCHASE;
            case 2:
                return STATUS;
            case 3:
                return CANCEL;
        }
        return null;
    }


}
