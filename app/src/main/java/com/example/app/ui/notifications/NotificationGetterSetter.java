package com.example.app.ui.notifications;

public class NotificationGetterSetter {
    private String nDetails;
    private String nImage;

    public NotificationGetterSetter() {
        // Default constructor required for Firebase
    }

    public NotificationGetterSetter(String nDetails, String nImage) {
        this.nDetails = nDetails;
        this.nImage = nImage;
    }

    public String getNotificationDetails() {
        return nDetails;
    }

    public void setNotificationDetails(String nDetails) {
        this.nDetails = nDetails;
    }

    public String getNotificationImage() {
        return nImage;
    }

    public void setNotificationImage(String nImage) {
        this.nImage = nImage;
    }
}