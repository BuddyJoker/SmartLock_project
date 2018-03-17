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
     * 人脸识别API
     */
    public static final String API_KEY = "Z2E7xx944efVaRncBaLnGrip";
    public static final String SECRET_KEY = "0Xp8fqsg4LEmD793DjwLF0ETGxQePqUs";
    public static final String authHost="https://aip.baidubce.com/oauth/2.0/token?";


    /**
     * 系统API
     */
    public static final String SUBSEVER_LIST="http://www.writebug.site/PrivateSmartHome/api/V1/GetSubserverList/username";
    public static final String LOGIN="http://www.writebug.site/PrivateSmartHome/api/V1/SignIn";
    public static final String LOGOUT="http://www.writebug.site/PrivateSmartHome/api/V1/SignOut";
    public static final String UPDATE_INFO="http://www.writebug.site/PrivateSmartHome/api/V1/UpdateUinfo";
    public static final String REGISTER="http://www.writebug.site/PrivateSmartHome/api/V1/SignUp";
    public static final String USER_INFO="http://www.writebug.site/PrivateSmartHome/api/V1/GetUinfo/username";
    public static final String QR_CODE="https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=fa9140accd95d143ce7bec711299e967/2934349b033b5bb571dc8c5133d3d539b600bc12.jpg";



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


