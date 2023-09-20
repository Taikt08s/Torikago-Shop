package com.group3.torikago.Torikago.Shop.util;

import jakarta.servlet.http.HttpServletRequest;

public class Util {
    public static String getSiteURL(HttpServletRequest request){
        String siteURL=request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }
}
