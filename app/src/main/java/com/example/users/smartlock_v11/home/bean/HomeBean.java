package com.example.users.smartlock_v11.home.bean;

/**
 * Created by BigBoss on 2017/8/11.
 */

public class HomeBean {

    /**
     * 成功
     * log_id:73473737
     */

    /**
     * 失败
     * error_code : 216616
     * log_id : 674786177
     * error_msg : image exist
     */

    private int error_code;
    private String log_id;
    private String error_msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
