package com.example.users.smartlock_v11.user.bean;

import java.util.List;

/**
 * Created by BigBoss on 2017/10/19.
 */

public class UserInfoBean {

    /**
     * log_id : 3314921889
     * result_num : 2
     * result : [{"uid":"uid1","user_info":"user info 1"},{"uid":"uid2","user_info":"user info 2"}]
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
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
         * uid : uid1
         * user_info : user info 1
         */

        private String uid;
        private String user_info;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_info() {
            return user_info;
        }

        public void setUser_info(String user_info) {
            this.user_info = user_info;
        }
    }
}
