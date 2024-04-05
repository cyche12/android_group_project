//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import android.app.Application;
import androidx.room.Room;

/**
 * The Sunrise Application class that initializes and provides the Room database to save results from past searches.
 */
public class SunriseApplication extends Application {
    private static SunDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), SunDatabase.class, "sunrise-database").build();
    }

    /**
     * Gets the singleton instance of the SunDatabase for use in classes that require the database.
     * @return The instance of SunDatabase.
     */
    public static SunDatabase getDatabase() {
        return database;
    }
}
