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
}
