package com.alvaro.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGenerator {
    public static String getDate(){
        try{
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
            String currentDate = date.format(new Date());
            return currentDate;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
