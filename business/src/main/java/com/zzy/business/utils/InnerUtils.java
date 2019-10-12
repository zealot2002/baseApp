package com.zzy.business.utils;

public class InnerUtils {
    public static String getRatingString(float score){
        if(score > 4){
            return "非常好";
        }else if(score > 3){
            return "好";
        }else if(score > 2){
            return "一般";
        }else if(score > 1){
            return "较差";
        }else if(score > 0){
            return "差";
        }else {
            return "非常好";
        }
    }
}
