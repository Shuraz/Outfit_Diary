package edu.csu.surajpokhrel.outfit_diary;

/**
 * Created by surajpokhrel on 16/9/17.
 */

public class UserInfo {
    public String name;
    public String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public UserInfo(){}
}
