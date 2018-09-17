package com.lognad.utils;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Helpers {
    /**
     * get all fields of the class.
     *
     * @param c
     * @return
     */
    public static List<Field> getAllFields(Class c) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(c.getDeclaredFields()));
        Class s = c.getSuperclass();
        if (s != null) {
            fields.addAll(getAllFields(s));
        }
        return fields;
    }

    /**
     * Converts epoch time to specified Zoned Date Time.
     *
     * @param zoneId
     * @param timeInMilliSec
     * @return
     */
    public static ZonedDateTime formatToZonedDateTime(String zoneId, long timeInMilliSec) {
        ZoneId zId = ZoneId.of(zoneId);
        Date d = new Date(timeInMilliSec);
        return d.toInstant().atZone(zId);
    }

    /**
     * Converts to base64 string.
     * @param data
     * @return
     */
    public static String toBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
}