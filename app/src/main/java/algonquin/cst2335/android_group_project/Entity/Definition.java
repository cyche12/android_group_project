package algonquin.cst2335.android_group_project.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "definitions",
        foreignKeys = @ForeignKey(entity = SearchTerm.class,
                parentColumns = "id",
                childColumns = "searchTermId",
                onDelete = ForeignKey.CASCADE))
public class Definition {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int searchTermId;
    public String definition;
}

