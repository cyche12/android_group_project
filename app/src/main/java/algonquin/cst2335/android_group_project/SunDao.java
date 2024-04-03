package algonquin.cst2335.android_group_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SunDao {

    /**
     * Inserts a new sunrise data entry into the database.
     *
     * @param sunriseData The sunrise data to insert.
     */
    @Insert
    void insert(Sunrise_Data sunriseData);

    /**
     * Retrieves all sunrise data entries stored in the database.
     *
     * @return A list of all sunrise data entries.
     */
    @Query("SELECT * FROM sunrise_data")
    List<Sunrise_Data> getSunriseData();
}
