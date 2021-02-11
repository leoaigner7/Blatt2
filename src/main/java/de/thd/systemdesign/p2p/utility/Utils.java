package de.thd.systemdesign.p2p.utility;

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String getNodeName(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (ip.matches(".*:.*:.*"))
            ip = String.format("[%s]", ip);
        return ip;
    }
}