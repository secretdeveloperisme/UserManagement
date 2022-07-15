package com.linhplus.UserManagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static DateUtil dateUtil ;
    private SimpleDateFormat simpleDateFormat;
    public DateUtil(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
    }
    public static DateUtil getInstance(){
        return dateUtil == null?new DateUtil():dateUtil;
    }
    public Date toDate(String dateString)  {
        try{
            Date date = simpleDateFormat.parse(dateString);
            return date;
        }
        catch (ParseException e) {
            return null;
        }
    }
    public boolean isValidDate (String dateString){
        try{
            simpleDateFormat.parse(dateString);
            return true;
        }
        catch (ParseException e) {
          return false;
        }
    }
}
