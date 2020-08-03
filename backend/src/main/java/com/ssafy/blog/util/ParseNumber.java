package com.ssafy.blog.util;

public class ParseNumber {
    
    public static boolean isParseLong(String id) {
        try {
            Long.parseLong(id);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}