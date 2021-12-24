package com.desk.spring.util;

import javax.servlet.http.HttpServletRequest;

public class ClientUtils {

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");

        // 프록시 환경
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        // 웹로직 서버
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
