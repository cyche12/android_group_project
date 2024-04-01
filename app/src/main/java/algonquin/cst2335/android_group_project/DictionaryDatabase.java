package algonquin.cst2335.android_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;

/**
 * Abstract database class for the dictionary application using Room persistence library.
 * This class defines the database configuration and serves as the main access point
 * for the underlying connection to the app's persisted data.
 *
 * It includes definitions for the {@link SearchTerm} and {@link Definition} entities
 * and provides an abstract method to get an instance of {@link DictionaryDao}.
 *
 * Purpose of the file: To define the Room database for the dictionary application,
 * including the entities it comprises and the DAOs it provides access to.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

@Database(entities = {SearchTerm.class, Definition.class}, version = 1)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDao dictionaryDao();
}
