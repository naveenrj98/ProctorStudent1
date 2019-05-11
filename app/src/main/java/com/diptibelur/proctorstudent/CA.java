package com.diptibelur.proctorstudent;

import java.util.Date;

/**
 * Created by ashokbelur on 4/15/2018.
 */

public class CA {
    private String announcementText;
    private String announcementUser;
    private String semesternumber;
    private long announcementTime;

    public CA(String announcementText, String announcementUser, String  semesternumber) {
        this.announcementText = announcementText;
        this.announcementUser = announcementUser;
        this.semesternumber = semesternumber;

        // Initialize to current time
        announcementTime = new Date().getTime();
    }

    public CA(CA atext, CA auser, CA asem){

    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setannouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public String getannouncementUser() {
        return announcementUser;
    }
    public String getSemesternumber() {
        return semesternumber;
    }


    public void setannouncementUser(String announcementUser) {
        this.announcementUser = announcementUser;
    }

    public void setSemesternumber(String semesternumber) {
        this.semesternumber = semesternumber;
    }

    public long getannouncementTime() {
        return announcementTime;
    }

    public void setannouncementTime(long announcementTime) {
        this.announcementTime = announcementTime;
    }
}
