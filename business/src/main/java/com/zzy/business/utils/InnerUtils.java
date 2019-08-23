package com.zzy.business.utils;

public class InnerUtils {
    public static String getRatingString(int score){
        if(score == 5){
            return "非常好";
        }else if(score == 4){
            return "好";
        }else if(score == 3){
            return "一般";
        }else if(score == 2){
            return "较差";
        }else if(score == 1){
            return "差";
        }
        return "";
    }
}
