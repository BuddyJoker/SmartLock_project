package com.example.users.smartlock_v11.lock.bean;

/**
 * Created by hp on 2018/3/22.
 */

public class ControlBean {

    /**
     * shareMode : true
     * ShareCode : fdafdafadsfdasf
     * subserverId : ae20ea77-ad6b-4029-b820-acfe0954e8ac
     * deviceIP : 192.168.1.100
     */

    private String shareMode;
    private String ShareCode;
    private String subserverId;
    private String deviceIP;

    public ControlBean(String mode,String code, String id, String deviceIP) {
        this.shareMode=mode;
        this.ShareCode=code;
        this.subserverId=id;
        this.deviceIP=deviceIP;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }

    public String getShareCode() {
        return ShareCode;
    }

    public void setShareCode(String ShareCode) {
        this.ShareCode = ShareCode;
    }

    public String getSubserverId() {
        return subserverId;
    }

    public void setSubserverId(String subserverId) {
        this.subserverId = subserverId;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }
}
