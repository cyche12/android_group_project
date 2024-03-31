package algonquin.cst2335.android_group_project.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;

@Dao
public interface DictionaryDao {
    @Insert
    long insertSearchTerm(SearchTerm searchTerm);

    @Insert
    void insertDefinition(Definition definition);

    @Query("SELECT * FROM search_terms")
    List<SearchTerm> getAllSearchTerms();

    @Query("SELECT * FROM search_terms WHERE id = :id")
    SearchTerm getSearchTermById(int id);

    @Query("SELECT * FROM definitions WHERE searchTermId = :searchTermId")
    List<Definition> getDefinitionsForSearchTerm(int searchTermId);

    @Delete
    void deleteDefinition(Definition definition);
}

