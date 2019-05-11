package com.diptibelur.proctorstudent;

/**
 * Created by ashokbelur on 4/13/2018.
 */

public class Proctor {
    public String branch;
    public String email;
    public String name;
    public String pass;
   // public String semesterschedule;

    public Proctor(){
    }

    public Proctor(String branch, String email, String name, String pass) {
        this.branch = branch;
        this.email = email;
        this.name = name;
        this.pass = pass;
       // this.semesterschedule = semesterschedule;

    }
    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public String getPass() {
        return pass;
    }

}
