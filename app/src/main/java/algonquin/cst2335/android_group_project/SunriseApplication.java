// SunriseApplication.java
package algonquin.cst2335.android_group_project;

import android.app.Application;

import androidx.room.Room;

public class SunriseApplication extends Application {

    private static SunDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Room database instance
        database = Room.databaseBuilder(getApplicationContext(),
                        SunDatabase.class, "sunrise-database")
                .build();
    }

    // Method to retrieve the database instance
    public static SunDatabase getDatabase() {
        return database;
    }
}
