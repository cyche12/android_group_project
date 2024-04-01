package algonquin.cst2335.android_group_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sunrise_data")
public class Sunrise_Data {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "latitude")
    protected String latitude;

    @ColumnInfo(name = "longitude")
    protected String longitude;

    @ColumnInfo(name = "sunrise_time")
    protected String sunriseTime;

    @ColumnInfo(name = "sunset_time")
    protected String sunsetTime;

}
