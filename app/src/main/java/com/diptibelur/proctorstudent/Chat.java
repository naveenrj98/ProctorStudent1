package com.diptibelur.proctorstudent;


import java.util.Date;

public class Chat {

    private String messageText;
    private String messageUser;
    private String messageTo;
    private long messageTime;

    public Chat(String messageText, String messageUser,String messageTo) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTo = messageTo;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public Chat(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo= messageTo;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}