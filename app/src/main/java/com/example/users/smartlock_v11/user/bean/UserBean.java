package com.example.users.smartlock_v11.user.bean;

import java.util.List;

/**
 * Created by BigBoss on 2017/8/18.
 */

public class UserBean {



    private int code;
    private String msg;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * avatar : /img/user_icon.png
         * post_id : 2646
         * saying : 嘿嘿
         * user_id : 261219
         * username : 下一个明天丶
         */

        private String post_id;
        private String saying;
        private String user_id;
        private String username;

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getSaying() {
            return saying;
        }

        public void setSaying(String saying) {
            this.saying = saying;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
