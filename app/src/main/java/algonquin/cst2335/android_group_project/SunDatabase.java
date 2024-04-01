package algonquin.cst2335.android_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sunrise_Data.class}, version = 1, exportSchema = false)
public abstract class SunDatabase extends RoomDatabase {


    public abstract SunDao sunriseDao();


}
