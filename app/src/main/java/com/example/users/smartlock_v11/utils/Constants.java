package com.example.users.smartlock_v11.utils;

public class Constants {
    //系统默认的模拟器就用这个ip
    //public static final String BASE = "http://182.92.5.3:8081/android/resources/";
    //使用第三方模拟器--当前电脑的ip地址
//    public static final String BASE = "http://192.168.51.104:8080";
    //运行到自己的真实手机上：
    //public static final String BASE = "http://192.168.191.1:8080";
    //公网ip
    public static final String BASE = "http://162.219.127.224:9999";

    // 请求Json数据基本URL
    public static final String BASE_URL_JSON = BASE + "/..../json/";

    /**
     * 获取token信息
     */
    public static final String API_KEY = "Z2E7xx944efVaRncBaLnGrip";
    public static final String SECRET_KEY = "0Xp8fqsg4LEmD793DjwLF0ETGxQePqUs";
    public static final String authHost="https://aip.baidubce.com/oauth/2.0/token?";


    /**
     * test
     *
     * 结果：此URL未能访问成功
     * 原因：需要配置Tomcat的以json格式的webservice
     * 所需技术：1.spring MVC
     *          2.IDE：MyEclipse
     *          3.web容器:tomcat
     *
     *
     * 终归还是太年轻了
     */
    public static final String USER_URL=BASE_URL_JSON+"USER_URL.json";


}


