package com.rental.api.core.util;

import org.springframework.util.StringUtils;

public class CommonUtil {

    public static String validAncConvertMacAddress(String mac) {
        if (!StringUtils.hasLength(mac) || mac.length() != 17) {
            return null;
        }
        return mac.replaceAll("-", ":");
    }
}
