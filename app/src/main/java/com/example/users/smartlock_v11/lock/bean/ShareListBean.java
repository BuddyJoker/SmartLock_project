package com.example.users.smartlock_v11.lock.bean;

import java.util.List;

/**
 * Created by hp on 2018/3/22.
 */

public class ShareListBean {

    /**
     * Devicenum : 11
     * DeviceInfo : [{"DeviceID":"000000","DeviceType":"0","DeviceIP":"192.168.1.101","DeviceNickname":"锁具0","SubserverId":"dfgdghdfg"},{"DeviceID":"000001","DeviceType":"0","DeviceIP":"192.168.1.102","DeviceNickname":"锁具1","SubserverId":"dfgdghdfg"},{"DeviceID":"000002","DeviceType":"0","DeviceIP":"192.168.1.103","DeviceNickname":"锁具2","SubserverId":"dfgdghdfg"},{"DeviceID":"000003","DeviceType":"0","DeviceIP":"192.168.1.104","DeviceNickname":"锁具3","SubserverId":"dfgdghdfg"},{"DeviceID":"000004","DeviceType":"0","DeviceIP":"192.168.1.105","DeviceNickname":"锁具4","SubserverId":"dfgdghdfg"},{"DeviceID":"000005","DeviceType":"0","DeviceIP":"192.168.1.106","DeviceNickname":"锁具5","SubserverId":"dfgdghdfg"},{"DeviceID":"000006","DeviceType":"0","DeviceIP":"192.168.1.107","DeviceNickname":"锁具6","SubserverId":"dfgdghdfg"},{"DeviceID":"000007","DeviceType":"0","DeviceIP":"192.168.1.108","DeviceNickname":"锁具7","SubserverId":"dfgdghdfg"},{"DeviceID":"000008","DeviceType":"0","DeviceIP":"192.168.1.109","DeviceNickname":"锁具8","SubserverId":"dfgdghdfg"},{"DeviceID":"000009","DeviceType":"0","DeviceIP":"192.168.1.1010","DeviceNickname":"锁具9","SubserverId":"dfgdghdfg"},{"DeviceID":"0000010","DeviceType":"0","DeviceIP":"192.168.1.1011","DeviceNickname":"锁具10","SubserverId":"dfgdghdfg"}]
     */


    private int Devicenum;
    private List<DeviceInfoBean> DeviceInfo;

    public int getDevicenum() {
        return Devicenum;
    }

    public void setDevicenum(int Devicenum) {
        this.Devicenum = Devicenum;
    }

    public List<DeviceInfoBean> getDeviceInfo() {
        return DeviceInfo;
    }

    public void setDeviceInfo(List<DeviceInfoBean> DeviceInfo) {
        this.DeviceInfo = DeviceInfo;
    }

    public static class DeviceInfoBean {
        /**
         * DeviceID : 000000
         * DeviceType : 0
         * DeviceIP : 192.168.1.101
         * DeviceNickname : 锁具0
         * SubserverId : dfgdghdfg
         */

        private String DeviceID;
        private String DeviceType;
        private String DeviceIP;
        private String DeviceNickname;
        private String SubserverId;

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String DeviceID) {
            this.DeviceID = DeviceID;
        }

        public String getDeviceType() {
            return DeviceType;
        }

        public void setDeviceType(String DeviceType) {
            this.DeviceType = DeviceType;
        }

        public String getDeviceIP() {
            return DeviceIP;
        }

        public void setDeviceIP(String DeviceIP) {
            this.DeviceIP = DeviceIP;
        }

        public String getDeviceNickname() {
            return DeviceNickname;
        }

        public void setDeviceNickname(String DeviceNickname) {
            this.DeviceNickname = DeviceNickname;
        }

        public String getSubserverId() {
            return SubserverId;
        }

        public void setSubserverId(String SubserverId) {
            this.SubserverId = SubserverId;
        }
    }
}
