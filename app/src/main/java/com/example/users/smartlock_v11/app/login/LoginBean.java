package com.example.users.smartlock_v11.app.login;

/**
 * Created by BigBoss on 2017/11/16.
 */

public class LoginBean {

    /**
     * username :
     * password :
     * Error_code : 0000  成功
     * msg : Login Successful
     * Error_code : 0005  失败
     * msg : Incorrect username or password
     */

    private String username;
    private String password;
    private String Error_code;
    private String msg;

    public LoginBean(String name, String pwd) {
        this.username=name;
        this.password=pwd;
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

    public String getError_code() {
        return Error_code;
    }

    public void setError_code(String Error_code) {
        this.Error_code = Error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
