package algonquin.cst2335.android_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Abstract database class for the sunrise application using Room persistence library.
 * This class defines the database configuration and serves as the main access point
 * for the underlying connection to the app's persisted data.
 * It includes a definition for the {@link Sunrise_Data} entity
 * and provides an abstract method to get an instance of {@link SunDao}.
 * Purpose of the file: To define the Room database for the sunrise application,
 * including the entity it comprises and the DAO it provides access to.
 * Author:
 * Lab Section:
 */

@Database(entities = {Sunrise_Data.class}, version = 1, exportSchema = false)
public abstract class SunDatabase extends RoomDatabase {

    /**
     * Provides access to the DAO (Data Access Object) for sunrise data operations.
     *
     * @return The DAO interface for interacting with sunrise data in the database.
     */
    public abstract SunDao sunDao();
}
