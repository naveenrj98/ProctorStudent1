package com.diptibelur.proctorstudent;

/**
 * Created by ashokbelur on 4/2/2018.
 */

public class Student {
    public String Name;
    public String Usn;
    public String Branch;
    public String Sem;
    public String Email;
    public String Pass;
    public String Repass;
    public String Proctor_Sem_Key;

    public Student(String Name, String Usn, String Branch, String Sem, String Email, String Pass, String Repass, String Proctor_Sem_Key)
    {
        this.Name=Name;
        this.Usn=Usn;
        this.Branch=Branch;
        this.Sem=Sem;
        this.Email=Email;
        this.Pass=Pass;
        this.Repass=Repass;
        this.Proctor_Sem_Key = Proctor_Sem_Key;

    }



}
