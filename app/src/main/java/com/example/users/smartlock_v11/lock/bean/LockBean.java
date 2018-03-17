package com.example.users.smartlock_v11.lock.bean;

import java.util.List;

/**
 * Created by BigBoss on 2017/8/19.
 */

public class LockBean {


    /**
     * {"log_id" : 73473737
     * "result_num" : 2
     * "result" : [{"ip":0,"port":1,"mac":44.3},{"ip":0,"port":1,"mac":44.3}]}
     */

    private int log_id;
    private int result_num;
    private List<ResultBean> result;
    /**
     * actoken :  b98774f1-3406-400f-8e51-407abe4d79f0
     * subserverId : b98774f1-3406-400f-8e51-407abe4d79f0
     * deviceIP : 192.168.1.1
     */

    private String actoken;
    private String subserverId;
    private String deviceIP;
    /**
     * error_code : 2000
     * msg : ControlMsg send success
     */

    private String error_code;
    private String msg;
    /**
     * SubserverNum : 2
     * SubserverInfo : [{"SubserverID":"000000","SubserverIP":"192.168.1.10 ","SubserverNickName":"树莓派0"},{"SubserverID":"000001","SubserverIP":"192.168.1.11 ","SubserverNickName":"树莓派1"}]
     */

    private int SubserverNum;
    private List<SubserverInfoBean> SubserverInfo;

    public LockBean(String actoken, String id, String deviceIP) {
        this.actoken=actoken;
        this.subserverId=id;
        this.deviceIP=deviceIP;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public String getActoken() {
        return actoken;
    }

    public void setActoken(String actoken) {
        this.actoken = actoken;
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

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSubserverNum() {
        return SubserverNum;
    }

    public void setSubserverNum(int SubserverNum) {
        this.SubserverNum = SubserverNum;
    }

    public List<SubserverInfoBean> getSubserverInfo() {
        return SubserverInfo;
    }

    public void setSubserverInfo(List<SubserverInfoBean> SubserverInfo) {
        this.SubserverInfo = SubserverInfo;
    }

    public static class ResultBean {
        /**
         * ip : 0
         * port : 1
         * mac : 44.3
         */

        private int ip;
        private int port;
        private double mac;

        public int getIp() {
            return ip;
        }

        public void setIp(int ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public double getMac() {
            return mac;
        }

        public void setMac(double mac) {
            this.mac = mac;
        }
    }

    public static class SubserverInfoBean {
        /**
         * SubserverID : 000000
         * SubserverIP : 192.168.1.10
         * SubserverNickName : 树莓派0
         */

        private String SubserverID;
        private String SubserverIP;
        private String SubserverNickName;

        public String getSubserverID() {
            return SubserverID;
        }

        public void setSubserverID(String SubserverID) {
            this.SubserverID = SubserverID;
        }

        public String getSubserverIP() {
            return SubserverIP;
        }

        public void setSubserverIP(String SubserverIP) {
            this.SubserverIP = SubserverIP;
        }

        public String getSubserverNickName() {
            return SubserverNickName;
        }

        public void setSubserverNickName(String SubserverNickName) {
            this.SubserverNickName = SubserverNickName;
        }
    }
}
