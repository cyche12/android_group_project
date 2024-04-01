package algonquin.cst2335.android_group_project.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
/**
 * Represents a definition entity in the database.
 * Each definition is linked to a specific search term by a foreign key,
 * allowing for cascading deletes. That is, deleting a search term will automatically
 * delete all its associated definitions.
 * Purpose of the file: To define the structure of the 'definitions' table in the database,
 * including its relationships with other tables such as the 'search_terms' table.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024]
 */
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

