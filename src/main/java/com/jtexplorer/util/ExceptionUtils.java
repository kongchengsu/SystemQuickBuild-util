package com.jtexplorer.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 王厚林
 */
public class ExceptionUtils {
    /**
     *
     * @param e
     * @param line 行数
     * @return
     */
    public static String getExceptionMessage(Exception e,Integer line){
        StringBuffer sb = new StringBuffer();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String[] errors = sw.toString().split("\r\n");
        if(line == null){
            line = errors.length;
        }
        for(int i = 0;i<errors.length && i<line;i++){
            sb.append(errors[i]);
        }
        return sb.toString();
    }
}
