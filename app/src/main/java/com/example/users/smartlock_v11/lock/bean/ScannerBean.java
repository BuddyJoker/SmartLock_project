package com.example.users.smartlock_v11.lock.bean;

/**
 * Created by hp on 2018/3/22.
 */

public class ScannerBean {

    /**
     * shareCode : fgfhjkgjtrhjkg
     */

    private String shareCode;
    /**
     * ReturnCode : 0000
     * msg : Share success
     */

    private String ReturnCode;
    private String msg;

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(String ReturnCode) {
        this.ReturnCode = ReturnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
