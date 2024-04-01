package algonquin.cst2335.android_group_project.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Entity class representing a search term in the database.
 * This class defines the structure of the 'search_terms' table where each search term
 * is stored with a unique identifier and the search term text itself.
 * Purpose of the file: To define the structure of the 'search_terms' table in the database,
 * facilitating the storage and retrieval of search terms used in the dictionary feature of the application.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */
@Entity(tableName = "search_terms")
public class SearchTerm {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String word;

}

