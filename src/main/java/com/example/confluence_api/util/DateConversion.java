package com.example.confluence_api.util;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateConversion 
{
    public static LocalDate stringToLocalDate(String dateString)
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_ZONED_DATE_TIME);

        return zonedDateTime.toLocalDate();
    }    
}
