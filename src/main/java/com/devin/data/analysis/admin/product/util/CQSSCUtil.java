package com.devin.data.analysis.admin.product.util;

public class CQSSCUtil {
    private static final String BIG = "1";
    private static final String SMALL = "2";
    private static final String ODD = "1";
    private static final String EVEN = "2";
    private static final String DRAGON = "1";
    private static final String TIGER = "2";
    private static final String DRAW = "3";

    public static final String URI = "http://f.apiplus.net/";
    public static final String CODE = "cqssc";
    public static final String ROWS = "20";
    public static final String FORMAT = ".json";
    public static final String SEPARATOR = "-";

    public static String isOdd(int a){
        if((a&1) != 1){   //是奇数
            return ODD;
        }
        return EVEN;
    }

    public static String isBig(int a){
        if(a > 22.5){
            return BIG;
        }
        return SMALL;
    }
    public static String isVingtEtun(int geDigit,int wanDigit){
        if(geDigit>wanDigit){
            return TIGER;
        }else if(geDigit<wanDigit){
            return DRAGON;
        }
        return DRAW;
    }
}
