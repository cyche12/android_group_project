package algonquin.cst2335.android_group_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sunrise_data")
public class Sunrise_Data {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "x_coordinate")
    protected String x_coordinate;

    @ColumnInfo(name = "y_coordinate")
    protected String y_coordinate;

    public String getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(String x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public String getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(String y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
}
