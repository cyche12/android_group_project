//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Abstract database class for the sunrise application using Room database.
 * It includes the entity it comprises and the DAO it provides access to.
 */

@Database(entities = {Sunrise_Data.class}, version = 1, exportSchema = false)
public abstract class SunDatabase extends RoomDatabase {

    /**
     * Provides access to the DAO for sunrise data.
     * @return The DAO interface for interacting with sunrise data in the database.
     */
    public abstract SunDao sunDao();
}
