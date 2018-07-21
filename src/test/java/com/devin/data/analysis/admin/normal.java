package com.devin.data.analysis.admin;

import com.devin.data.analysis.admin.core.date.DateUtil;

import java.time.LocalDateTime;

public class normal {

    public static void main(String[] args) {
       if( 10 % 500 == 0){
           System.out.println("heheh");
       }
        String strDatewithTime = "2015-08-04 10:11:30";
        LocalDateTime aLDT = LocalDateTime.parse(strDatewithTime,DateUtil.DATA_FORMATTER);
        System.out.println("Date with Time: " + aLDT);

    }
}
