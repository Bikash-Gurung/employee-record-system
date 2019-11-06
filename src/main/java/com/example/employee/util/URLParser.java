package com.example.employee.util;

import javax.servlet.http.HttpServletRequest;

public class URLParser {
    public String getURLParam(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            return pathParts[1];
        }

        return null;
    }
}
