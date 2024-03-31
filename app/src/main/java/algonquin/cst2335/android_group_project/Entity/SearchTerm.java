package algonquin.cst2335.android_group_project.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_terms")
public class SearchTerm {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String word;

}

