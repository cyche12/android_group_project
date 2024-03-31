package algonquin.cst2335.android_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;

@Database(entities = {SearchTerm.class, Definition.class}, version = 1)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDao dictionaryDao();
}
