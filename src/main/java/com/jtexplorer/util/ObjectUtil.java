package com.jtexplorer.util;

import java.util.Map;
import java.util.Set;

public class ObjectUtil {

    static boolean  notNull(Object obj)
    {
        return obj == null;
    }

    public static boolean isNullOrEmpty(Map<?, ?> map)
    {
        return (map==null || map.isEmpty());
    }

    static boolean isNullOrEmpty(Set<?> set)
    {
        return (set==null || set.isEmpty());
    }

    static boolean notNullAndPositive(Object obj){
        if(obj != null )
        {
            if (obj instanceof Long )
            {
                return (Long)obj >0;
            }else if(obj instanceof Short)
            {
                return (Short)obj >0;
            }else if(obj instanceof Integer)
            {
                return (Integer)obj >0;
            }else if(obj instanceof Byte)
            {
                return (Integer)obj >0;
            }else if(obj instanceof Double)
            {
                return (Double)obj >0;
            }
        }
        return false;
    }
}
