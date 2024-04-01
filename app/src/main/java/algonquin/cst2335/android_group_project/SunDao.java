package algonquin.cst2335.android_group_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SunDao {
    @Insert
    void insert(Sunrise_Data sunriseData);

    @Query("SELECT * FROM sunrise_data")
    List<Sunrise_Data> getSunriseData();
}
