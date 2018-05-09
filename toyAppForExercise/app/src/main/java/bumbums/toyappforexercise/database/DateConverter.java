package bumbums.toyappforexercise.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by hanseungbeom on 2018. 5. 9..
 */

public class DateConverter {

    //room will use this method when reading from the database
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    //room will use this method when writing into the database
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
