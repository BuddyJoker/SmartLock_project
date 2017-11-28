package com.example.users.smartlock_v11.app.login;

/**
 * Created by BigBoss on 2017/11/16.
 */

public class LoginBean {

    /**
     * username :
     * password :
     * "AccessToken":"65266319-4f03-4f99-a194-5d1a29a34cfd" 成功
     * Error_code : 0001  失败
     * msg : JSON format error 发送的JSON包格式不符合要求
     * Error_code : 0002  失败
     * msg : Username contains dangerous characters 用户名包含危险字符
     * Error_code : 0003  失败
     * msg : Password contains dangerous characters 密码包含危险字符
     * Error_code : 0004  失败
     * msg : Password contains dangerous characters 用户名不存在
     * Error_code : 0005  失败
     * msg : Incorrect username or password 密码错误
     */

    private String username;
    private String password;
    private String error_code;
    private String msg;
    /**
     * AccessToken : 65266319-4f03-4f99-a194-5d1a29a34cfd
     */

    private String AccessToken;

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
        return error_code;
    }

    public void setError_code(String Error_code) {
        this.error_code = Error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }
}
