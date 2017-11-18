package com.example.users.smartlock_v11.app.register;

/**
 * Created by BigBoss on 2017/11/17.
 */

public class RegisterBean {

    /**
     * username :
     * password : 123456
     * email : 1059392229@qq.com
     * phonenum : 15713660311
     * sex : M
     */

    private String username;
    private String password;
    private String email;
    private String phonenum;
    private String sex;

    public RegisterBean(String name,String passwd){
        this.username=name;
        this.password=passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
