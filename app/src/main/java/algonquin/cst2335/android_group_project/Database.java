package algonquin.cst2335.android_group_project;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {SongReturn.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract SongReturnDAO cmDAO();
}